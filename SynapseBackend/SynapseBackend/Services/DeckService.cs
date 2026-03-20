using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations.Operations;
using SynapseBackend.Data;
using SynapseBackend.Data.DTOs;
using SynapseBackend.Entities;

namespace SynapseBackend.Services;

public class DeckService(AppDbContext _context) : IDeckService
{
    public async Task<List<DeckDto>> GetDecksAsync(string userId)
    {
        return await _context.Decks
            .Where(d => d.OwnerId == userId)
            .Where(d => d.IsDeleted == false)
            .Select(d => new DeckDto(d.Id.ToString(), d.Name, d.Description, d.OwnerId, d.ColorSeed, d.LastModified))
            .ToListAsync();
    }

    public async Task SyncDecks(string userId, List<DeckDto> incomingDecks)
    {
        foreach (var dto in incomingDecks)
        {
            if (!Guid.TryParse(dto.Id, out Guid deckId)) continue;

            var existing = await _context.Decks.FindAsync(deckId);

            if (existing == null)
            {
                _context.Decks.Add(
                    new Deck
                    {
                        Id = deckId,
                        OwnerId = userId,
                        Name = dto.Name,
                        Description = dto.Description,
                        ColorSeed = dto.ColorSeed,
                        LastModified = dto.LastModified,
                        IsDeleted = dto.IsDeleted
                    });
            }else if (existing.OwnerId == userId && dto.LastModified > existing.LastModified)
            {
                existing.Name = dto.Name;
                existing.Description = dto.Description;
                existing.ColorSeed = dto.ColorSeed;
                existing.LastModified = dto.LastModified;
                existing.IsDeleted = dto.IsDeleted;
            }
        }

        await _context.SaveChangesAsync();
    }
}