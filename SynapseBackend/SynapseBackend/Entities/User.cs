using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace SynapseBackend.Entities;

[Table("Users")]
public class User
{
    [Key] public string GoogleSubjectId { get; set; }

    [MaxLength(100)]
    public required string DisplayName { get; set; }
    [MaxLength(100)]
    public required string Email { get; set; }
    [MaxLength(50)]
    public string AuthProvider { get; set; } = "Google";

    public ICollection<Deck> Decks { get; set; } = new List<Deck>();
}