package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olidev.synapse_mobile.data.repository.DeckRepository
import com.olidev.synapse_mobile.data.repository.FlashcardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeckDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deckRepository: DeckRepository,
    private val flashcardRepository: FlashcardRepository
) : ViewModel() {
    private val deckId: String =
        savedStateHandle["deckId"] ?: throw IllegalArgumentException("Deck ID is required")

    var isAddCardSheetVisible by mutableStateOf(false)

    val uiState: StateFlow<DeckDetailsUiState> = combine(
        deckRepository.getDeckById(deckId),
        flashcardRepository.getCardsByDeck(deckId)
    ) { deck, flashcards ->
        DeckDetailsUiState(
            isLoading = false,
            deck = deck,
            flashcards = flashcards,
            error = null
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DeckDetailsUiState(isLoading = true)
        )

    fun addCard(front: String, back: String) {
        viewModelScope.launch {
            flashcardRepository.addCard(front, back, deckId)
        }
    }

    fun toggleAddCardSheet(visible: Boolean) {
        isAddCardSheetVisible = visible
    }

    fun updateDeck(newName: String, newColorSeed: Int) {
        val currentDeck = uiState.value.deck ?: return
        viewModelScope.launch {
            deckRepository.updateDeck(currentDeck.copy(name = newName, colorSeed = newColorSeed))
        }
    }

    fun deleteDeck() {
        val currentDeck = uiState.value.deck ?: return
        viewModelScope.launch {
            deckRepository.deleteDeck(currentDeck)
            /*TODO: ADD NAVIGATION AFTER DECK DELETE*/
        }
    }
}
