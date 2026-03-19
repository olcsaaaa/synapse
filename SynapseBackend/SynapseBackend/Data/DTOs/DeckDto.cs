namespace SynapseBackend.Data.DTOs;

public record DeckDto(string Id, string Name, string Description, string OwnerId, int ColorSeed);