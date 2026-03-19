package com.olidev.synapse_mobile.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.olidev.synapse_mobile.data.local.entities.Deck
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks WHERE ownerId = :userId")
    fun getAllDecksByUser(userId : String): Flow<List<Deck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: Deck) : Long

    @Update
    suspend fun updateDeck(deck: Deck) : Int

    @Delete
    suspend fun deleteDeck(deck: Deck) : Int

}