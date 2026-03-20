using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SynapseBackend.Data.DTOs;
using SynapseBackend.Services;

namespace SynapseBackend.Controllers
{
    [Authorize]
    [Route("api/decks")]
    [ApiController]
    public class DecksController(IDeckService deckService) : ControllerBase
    {
        private string UserId => User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value ?? throw new UnauthorizedAccessException();
        
        [HttpGet]
        public async Task<IActionResult> GetDecks()
        {
            return Ok(await deckService.GetDecksAsync(UserId));
        }

        [HttpPost("sync")]
        public async Task<IActionResult> Sync([FromBody] List<DeckDto> decks)
        {
            await deckService.SyncDecks(UserId, decks);
            return Ok();
        }
        
    }
}