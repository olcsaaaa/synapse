using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Google.Apis.Auth;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using SynapseBackend.Data;
using SynapseBackend.Data.DTOs;
using SynapseBackend.Entities;
using JwtRegisteredClaimNames = Microsoft.IdentityModel.JsonWebTokens.JwtRegisteredClaimNames;

namespace SynapseBackend.Services;

public class AuthService : IAuthService
{
    private readonly AppDbContext _dbContext;
    private readonly IConfiguration _configuration;

    public AuthService(AppDbContext dbContext, IConfiguration configuration)
    {
        _dbContext = dbContext;
        _configuration = configuration;
    }

    public async Task<AuthResponse> AuthenticateGoogleUserAsync(string idToken)
    {
        var settings = new GoogleJsonWebSignature.ValidationSettings
        {
            Audience = new[] { _configuration["Google:ClientId"] }
        };
        GoogleJsonWebSignature.Payload payload;

        try
        {
            payload = await GoogleJsonWebSignature.ValidateAsync(idToken, settings);
        }
        catch (InvalidJwtException)
        {
            throw new UnauthorizedAccessException("Invalid JWT");
        }

        var user = await _dbContext.Users.FirstOrDefaultAsync(x => x.GoogleSubjectId == payload.Subject);

        if (user != null) return GenerateToken(user);
        user = new User
        {
            GoogleSubjectId = payload.Subject,
            Email = payload.Email,
            DisplayName = payload.Name ?? payload.GivenName ?? payload.FamilyName ?? "Synapse User"
        };
        _dbContext.Users.Add(user);
        await _dbContext.SaveChangesAsync();

        return GenerateToken(user);
    }

    private AuthResponse GenerateToken(User user)
    {
        var jwtSettings = _configuration.GetSection("Jwt");
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(jwtSettings["Key"]!));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
        var expiration = DateTime.UtcNow.AddDays(7);

        var claims = new[]
        {
            new Claim(JwtRegisteredClaimNames.Sub, user.GoogleSubjectId),
            new Claim(JwtRegisteredClaimNames.Email, user.Email),
            new Claim(JwtRegisteredClaimNames.Name, user.DisplayName),
            new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
        };
        var token = new JwtSecurityToken(
            issuer: jwtSettings["Issuer"],
            audience: jwtSettings["Audience"],
            claims: claims,
            expires: expiration,
            signingCredentials: creds
        );
        return new AuthResponse(
            new JwtSecurityTokenHandler().WriteToken(token),
            user.GoogleSubjectId,
            ((DateTimeOffset)expiration).ToUnixTimeSeconds());
    }
}