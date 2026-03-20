package com.olidev.synapse_mobile.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards WHERE deckId = :deckId AND isDeleted = 0 ORDER BY lastModified DESC")
    fun getCardsByDeck(deckId: String): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Flashcard): Long

    @Query("SELECT * FROM flashcards WHERE isSynced = 0 AND isDeleted = 0")
    suspend fun getUnsyncedCards(): List<Flashcard>

    @Query("UPDATE flashcards SET isSynced = 1 WHERE id IN (:cardIds)")
    suspend fun markAsSynced(cardIds: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCards(cards: List<Flashcard>)

    @Update
    suspend fun updateCard(card: Flashcard): Int

    @Query("SELECT * FROM flashcards WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun getPendingDeletes(): List<Flashcard>

    @Query("UPDATE flashcards SET isDeleted = 1, isSynced = 0, lastModified = :timestamp WHERE id IN (:cardIds)")
    suspend fun markAsDeleted(cardIds: List<String>, timestamp: Long = System.currentTimeMillis())

    @Delete
    suspend fun hardDeleteCard(card: Flashcard): Int

}


