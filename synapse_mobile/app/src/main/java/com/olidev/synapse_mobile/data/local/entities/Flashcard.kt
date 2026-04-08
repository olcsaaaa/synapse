package com.olidev.synapse_mobile.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = Deck::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("deckId")]
)
data class Flashcard(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val deckId : String,
    val front: String,
    val back: String,
    val isSynced : Boolean = false,
    val lastModified : Long = System.currentTimeMillis(),
    val isDeleted : Boolean = false
)
