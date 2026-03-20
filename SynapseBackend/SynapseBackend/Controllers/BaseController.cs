using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace SynapseBackend.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class BaseController : ControllerBase
    {
        protected string UserId => User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value ??
                                   throw new UnauthorizedAccessException();
    }
}