using SynapseBackend.Data.DTOs;

namespace SynapseBackend.Services;

public interface IDeckService
{
    Task<List<DeckDto>> GetDecksAsync(string userId);
    Task SyncDecks(string userId, List<DeckDto> incomingDecks);
}