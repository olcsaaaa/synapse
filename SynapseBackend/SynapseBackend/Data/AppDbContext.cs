using Microsoft.EntityFrameworkCore;
using SynapseBackend.Entities;

namespace SynapseBackend.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<User> Users { get; set; }
    public DbSet<Deck> Decks { get; set; }
    public DbSet<Flashcard> Flashcards { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<User>().HasIndex(u => u.Email).IsUnique();
        modelBuilder.Entity<Deck>().HasIndex(d => d.Name).IsUnique();
        modelBuilder.Entity<User>().HasMany(u => u.Decks).WithOne(d => d.Owner).OnDelete(DeleteBehavior.Cascade);
        
        modelBuilder.Entity<Deck>()
            .Property(d => d.IsShared)
            .HasDefaultValue(false);
        
        modelBuilder.Entity<Deck>()
            .Property(d => d.IsReminderEnabled)
            .HasDefaultValue(false);
        
        modelBuilder.Entity<Deck>()
            .HasOne(d=>d.Owner)
            .WithMany(u=>u.Decks)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Flashcard>()
            .HasOne(f => f.Deck)
            .WithMany(d => d.Flashcards)
            .HasForeignKey(f => f.DeckId);
    }
}