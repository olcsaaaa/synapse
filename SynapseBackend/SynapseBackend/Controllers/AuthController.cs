using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SynapseBackend.Data.DTOs;
using SynapseBackend.Services;

namespace SynapseBackend.Controllers
{
    [Route("api/auth")]
    [ApiController]
    public class AuthController(IAuthService authService) : ControllerBase
    {
        [HttpPost("google")]
        public async Task<IActionResult> AuthenticateGoogleUser([FromBody] TokenRequest tokenRequest)
        {
            if (string.IsNullOrWhiteSpace(tokenRequest.IdToken))
                return BadRequest(new { message = "ID token required" });

            try
            {
                var response = await authService.AuthenticateGoogleUserAsync(tokenRequest.IdToken);
                return Ok(response);
            }
            catch (UnauthorizedAccessException e)
            {
                return Unauthorized(new { message = e.Message });
            }
            catch (Exception e)
            {
                return StatusCode(500,new { message = "Something went wrong" });
            }
        }
    }
}