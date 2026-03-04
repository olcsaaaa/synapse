package com.olidev.synapse_mobile.ui.decks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.data.repository.DeckRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeckViewModel(private val deckRepository: DeckRepository) : ViewModel() {

    val deckUiState: StateFlow<List<Deck>> = deckRepository.getAllDecksByUserId("test_user")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun addDeck(name: String, description: String) {
        viewModelScope.launch {
            val newDeck = Deck(name = name, description = description, ownerId = "test_user")
            deckRepository.insertDeck(newDeck)
        }
    }
}