namespace SynapseBackend.Data.DTOs;

public record AuthResponse(string Token, string UserId, long ExpiresAt);