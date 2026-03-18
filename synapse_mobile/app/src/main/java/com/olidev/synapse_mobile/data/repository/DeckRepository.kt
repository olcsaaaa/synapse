package com.olidev.synapse_mobile.data.repository

import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.local.entities.Deck
import kotlinx.coroutines.flow.Flow

class DeckRepository(private val deckDao: DeckDao, private val api: SynapseApi) {

    fun getAllDecksByUserId(userId: String) : Flow<List<Deck>> = deckDao.getAllDecksByUser(userId)

    suspend fun insertDeck(deck: Deck){
        deckDao.insertDeck(deck)
    }

    suspend fun updateDeck(deck: Deck){
        deckDao.updateDeck(deck)
    }

    suspend fun deleteDeck(deck: Deck){
        deckDao.deleteDeck(deck)
    }

}
