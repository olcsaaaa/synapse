package com.olidev.synapse_mobile.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.olidev.synapse_mobile.data.local.entities.Deck
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks WHERE ownerId = :userId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun getAllDecksByUser(userId : String): Flow<List<Deck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: Deck) : Long

    @Query("SELECT * FROM decks WHERE isSynced = 0 AND isDeleted = 0")
    suspend fun getUnsyncedDecks():List<Deck>

    @Query("UPDATE decks SET isSynced = 1 WHERE id IN (:deckIds)")
    suspend fun markAsSynced(deckIds: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDecks(decks: List<Deck>)

    @Update
    suspend fun updateDeck(deck: Deck) : Int

    @Query("SELECT * FROM decks WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun getPendingDeletes(): List<Deck>

    @Query("UPDATE decks SET isDeleted = 1, isSynced = 0 WHERE id IN (:deckIds)")
    suspend fun markAsDeleted(deckIds: List<String>)

    @Delete
    suspend fun hardDeleteDeck(deck: Deck) : Int

}