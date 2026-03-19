using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;
using Microsoft.IdentityModel.Tokens;

namespace SynapseBackend.Entities;

public class Deck
{
    [Key] public Guid Id { get; set; }

    [ForeignKey("OwnerId")] public string OwnerId { get; set; }
    [MaxLength(100)] public required string Name { get; set; }
    [MaxLength(1000)]public string Description { get; set; } = string.Empty;

    public bool IsShared { get; set; } = false;

    public bool IsReminderEnabled { get; set; } = false;

    public long? NextReviewTime { get; set; }

    public long LastModified { get; set; } = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
    public required int ColorSeed { get; set; }

    public User Owner { get; set; }
}