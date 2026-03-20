package com.olidev.synapse_mobile.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.local.dtos.DeckDto
import com.olidev.synapse_mobile.data.local.dtos.toEntity
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.workers.DeckSyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val deckDao: DeckDao,
    private val api: SynapseApi,
    @param:ApplicationContext private val context: Context
) {

    fun getAllDecksByUserId(userId: String): Flow<List<Deck>> = deckDao.getAllDecksByUser(userId)

    suspend fun insertDeck(deck: Deck) {
        deckDao.insertDeck(deck.copy(isSynced = false))
        triggerBackgroundSync(deck.id)
    }

    suspend fun updateDeck(deck: Deck) {
        deckDao.updateDeck(deck.copy(isSynced = false))

        triggerBackgroundSync(deck.id)
    }

    suspend fun syncDecks(userId: String) {
        try {
            val remoteDecks: List<DeckDto> = api.getAllDecks(userId).body() ?: emptyList()

            val freshDecks: List<Deck> =
                remoteDecks.map { it.toEntity(isSynced = true, ownerId = userId) }

            deckDao.upsertDecks(freshDecks)
        } catch (e: Exception) {
        }
    }

    suspend fun deleteDeck(deck: Deck) {
        deckDao.markAsDeleted(listOf(deck.id))
        triggerBackgroundSync(deck.id)
    }

    private suspend fun triggerBackgroundSync(deckId: String) {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncRequest = OneTimeWorkRequestBuilder<DeckSyncWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "sync_deck_$deckId",
                ExistingWorkPolicy.REPLACE,
                syncRequest
            )
        } catch (e: Exception) {
        }
    }

}
