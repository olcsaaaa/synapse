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
    @Query("SELECT * FROM flashcards WHERE deckId = :deckId")
    fun getCardsByDeck(deckId : String): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card : Flashcard) : Long

    @Update
    suspend fun updateCard(card : Flashcard) : Int

    @Delete
    suspend fun deleteCard(card : Flashcard) : Int

}


