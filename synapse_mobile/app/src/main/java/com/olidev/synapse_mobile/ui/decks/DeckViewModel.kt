package com.olidev.synapse_mobile.ui.decks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.data.repository.DeckRepository
import com.olidev.synapse_mobile.ui.home.DeckWithCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.flatMapLatest
import java.util.UUID
import kotlin.collections.emptyList

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val deckRepository: DeckRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val deckUiState: StateFlow<List<DeckWithCount>> = sessionManager.userId
        .flatMapLatest { userId ->
            if (userId != null) {
                deckRepository.getDecksWithCount(userId)
            } else {
                kotlinx.coroutines.flow.flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addDeck(name: String, description: String, seed: Int) {
        viewModelScope.launch {
            val uid = sessionManager.userId.first()

            if (uid != null) {
                val newDeck = Deck(
                    id = UUID.randomUUID().toString(),
                    ownerId = uid,
                    name = name,
                    description = description,
                    colorSeed = seed,
                    lastModified = System.currentTimeMillis(),
                    isSynced = false
                )
                deckRepository.insertDeck(newDeck)
            }else{
                return@launch
            }

        }
    }
}