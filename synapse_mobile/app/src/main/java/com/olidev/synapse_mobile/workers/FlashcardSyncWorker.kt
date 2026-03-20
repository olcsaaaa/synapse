package com.olidev.synapse_mobile.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.FlashcardDao
import com.olidev.synapse_mobile.data.local.dtos.toDto
import com.olidev.synapse_mobile.data.local.dtos.toEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class FlashcardSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val flashcardDao: FlashcardDao,
    private val api: SynapseApi,
    private val sessionManager: SessionManager
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        sessionManager.userId.first() ?: return Result.failure()

        return try {
            val pendingDeletes = flashcardDao.getPendingDeletes()
            for (flashcard in pendingDeletes) {
                val res = api.deleteFlashcard(flashcard.id)
                if (res.isSuccessful || res.code() == 404) {
                    flashcardDao.hardDeleteCard(flashcard)
                }
            }

            val unsyncedCards = flashcardDao.getUnsyncedCards()
            if (unsyncedCards.isNotEmpty()) {
                val pushResponse = api.syncFlashcards(unsyncedCards.map { it.toDto() })
                if (pushResponse.isSuccessful) {
                    flashcardDao.markAsSynced(unsyncedCards.map { it.id })
                } else {
                    return Result.retry()
                }
            }

            val pullResponse = api.getAllFlashcards()
            if (pullResponse.isSuccessful) {
                val remoteCards = pullResponse.body() ?: emptyList()
                val freshCards = remoteCards.map { it.toEntity(isSynced = true) }
                flashcardDao.upsertCards(freshCards)
            } else {
                Result.retry()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
