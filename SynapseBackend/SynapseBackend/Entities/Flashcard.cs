using System.ComponentModel.DataAnnotations;

namespace SynapseBackend.Entities;

public class Flashcard
{
    [Key]
    public Guid Id { get; set; }
    public Guid DeckId { get; set; }
    
    [MaxLength(512)]
    public required String Front { get; set; }
    [MaxLength(1024)]
    public required String Back { get; set; }
    public long LastModified { get; set; } = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
    public bool IsDeleted { get; set; } = false;

    public Deck Deck { get; set; } = null!;
}