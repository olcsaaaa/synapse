using SynapseBackend.Data.DTOs;

namespace SynapseBackend.Services;

public interface IAuthService
{
    Task<AuthResponse> AuthenticateGoogleUserAsync(string idToken);
}