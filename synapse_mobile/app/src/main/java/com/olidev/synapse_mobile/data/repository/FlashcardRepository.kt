package com.olidev.synapse_mobile.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.FlashcardDao
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import com.olidev.synapse_mobile.workers.FlashcardSyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FlashcardRepository @Inject constructor(
    private val flashcardDao: FlashcardDao,
    private val api: SynapseApi,
    @param:ApplicationContext private val context: Context
) {

    fun getCardsByDeck(deckId: String): Flow<List<Flashcard>> = flashcardDao.getCardsByDeck(deckId)

    suspend fun addCard(front: String, back: String, deckId: String) {
        val card = Flashcard(
            front = front,
            back = back,
            deckId = deckId,
            isSynced = false,
            isDeleted = false,
            lastModified = System.currentTimeMillis()
        )
        flashcardDao.insertCard(card)
        triggerBackgroundSync(card.id)
    }

    suspend fun updateCard(card: Flashcard) {
        flashcardDao.updateCard(card.copy(isSynced = false, lastModified = System.currentTimeMillis()))
    }

    suspend fun deleteCard(card: Flashcard) {
        flashcardDao.markAsDeleted(listOf(card.id))
        triggerBackgroundSync(card.id)
    }

    private fun triggerBackgroundSync(id: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<FlashcardSyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "sync_card_$id",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}
