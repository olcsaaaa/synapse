package com.olidev.synapse_mobile.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import com.olidev.synapse_mobile.data.repository.FlashcardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel@Inject constructor(
    private val repository: FlashcardRepository
) : ViewModel() {


    data class PracticeUiState(
        val activeCards: List<Flashcard> = emptyList(),
        val isLoading: Boolean = true
    )


    private val _uiState = MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    fun loadDeck(deckId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getCardsByDeck(deckId).collect { realCards ->
                _uiState.update {
                    it.copy(
                        activeCards = realCards,
                        isLoading = false
                    )
                }
            }
        }
    }


    fun processSwipe(swipedCard: Flashcard, isSuccess: Boolean) {
        _uiState.update { currentState ->
            val updatedCards = currentState.activeCards.toMutableList()
            if (updatedCards.isNotEmpty()) {
                val currentCard = updatedCards.removeAt(0)
                if (!isSuccess) {
                    updatedCards.add(currentCard)
                }
            }
            currentState.copy(activeCards = updatedCards)
        }
    }
}