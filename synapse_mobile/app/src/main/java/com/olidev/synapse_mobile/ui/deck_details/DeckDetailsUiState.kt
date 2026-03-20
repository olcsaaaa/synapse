package com.olidev.synapse_mobile.ui.deck_details

import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.data.local.entities.Flashcard

data class DeckDetailsUiState(
    val isLoading: Boolean = true,
    val deck: Deck? = null,
    val flashcards: List<Flashcard> = emptyList(),
    val error: String? = null
)
