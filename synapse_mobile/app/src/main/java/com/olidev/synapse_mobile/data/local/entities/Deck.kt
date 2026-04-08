package com.olidev.synapse_mobile.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "decks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ownerId")]
)
data class Deck(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ownerId : String,
    val name: String,
    val description: String ="",
    val isShared : Boolean = false,
    val isReminderEnabled : Boolean = false,
    val nextReviewDate : Long? = null,
    val colorSeed : Int,
    val isSynced : Boolean = false,
    val lastModified : Long = System.currentTimeMillis(),
    val isDeleted : Boolean = false
)
