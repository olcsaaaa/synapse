package com.olidev.synapse_mobile.data.repository

import com.olidev.synapse_mobile.data.local.daos.FlashcardDao
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val flashcardDao: FlashcardDao) {

    fun getCardsByDeck(deckId: String) : Flow<List<Flashcard>> = flashcardDao.getCardsByDeck(deckId)

    suspend fun addCard(frontText: String, backText: String, deckId : String){
        val card = Flashcard(
            deckId = deckId,
            front = frontText,
            back = backText)

        flashcardDao.insertCard(card)
    }

    suspend fun updateCard(card: Flashcard) = flashcardDao.updateCard(card)

    suspend fun deleteCard(card: Flashcard) = flashcardDao.deleteCard(card)
}