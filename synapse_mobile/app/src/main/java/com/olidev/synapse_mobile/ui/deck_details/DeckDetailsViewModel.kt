package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olidev.synapse_mobile.data.local.entities.Flashcard
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
    private val flashcardRepository: FlashcardRepository,
) : ViewModel() {
    private val deckId: String =
        savedStateHandle["deckId"] ?: throw IllegalArgumentException("Deck ID is required")


    var isAddCardSheetVisible by mutableStateOf(false)
    var editingCard by mutableStateOf<Flashcard?>(null)
        private set

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

    fun toggleAddCardSheet(visible: Boolean) {
        isAddCardSheetVisible = visible
    }


    fun updateDeck(newName: String, newDescription: String, newColorSeed: Int) {
        val currentDeck = uiState.value.deck ?: return
        viewModelScope.launch {
            deckRepository.updateDeck(currentDeck.copy(name = newName, description = newDescription, colorSeed = newColorSeed))
        }
    }

    fun deleteDeck(onComplete : ()-> Unit) {
        val currentDeck = uiState.value.deck ?: return
        viewModelScope.launch {
            deckRepository.deleteDeck(currentDeck)
            onComplete()
        }
    }

    fun deleteCard(currentCard: Flashcard){
        viewModelScope.launch {
            flashcardRepository.deleteCard(currentCard)
        }
    }

    fun startEditingCard(card: Flashcard) {
        editingCard = card
        toggleAddCardSheet(true)
    }

    fun clearEditingState() {
        editingCard = null
        toggleAddCardSheet(false)
    }

    fun saveCard(front: String, back: String) {
        val currentCard = editingCard
        viewModelScope.launch {
            if (currentCard != null) {
                flashcardRepository.updateCard(currentCard.copy(front = front, back = back))
            } else {
                val deckId = uiState.value.deck?.id ?: return@launch
                flashcardRepository.addCard(front, back, deckId)
            }
            clearEditingState()
        }
    }

}
