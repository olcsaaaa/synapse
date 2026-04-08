package com.olidev.synapse_mobile.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.local.dtos.toEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlin.collections.emptyList

@HiltWorker
class DeckSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val deckDao: DeckDao,
    private val api: SynapseApi,
    private val sessionManager: SessionManager
) : CoroutineWorker( context, workerParams) {

    override suspend fun doWork(): Result {
        val userId = sessionManager.userId.first() ?: return Result.failure()

        return try {
            val pendingDeletes = deckDao.getPendingDeletes()

            for (deck in pendingDeletes) {
                val res = api.deleteDeck(deck.id)

                if (res.isSuccessful || res.code() == 404) {
                    deckDao.hardDeleteDeck(deck)
                }
            }


            val unsyncedDecks = deckDao.getUnsyncedDecks()
            if (unsyncedDecks.isNotEmpty()) {
                val pushResponse = api.syncDecks(unsyncedDecks)

                if(pushResponse.isSuccessful){
                    deckDao.markAsSynced(unsyncedDecks.map { it.id })
                }else{
                    return Result.retry()
                }
            }

            val pullResponse = api.getAllDecks(userId)
            if (pullResponse.isSuccessful) {
                val decks = pullResponse.body() ?: emptyList()
                val freshDecks = decks.map { it.toEntity(ownerId = userId, isSynced = true) }
                deckDao.upsertDecks(freshDecks)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }


    }
}