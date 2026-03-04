package com.olidev.synapse_mobile.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.olidev.synapse_mobile.Synapse
import com.olidev.synapse_mobile.ui.decks.DeckViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            val application = synapse()
            DeckViewModel(application.deckRepository)
        }

    }
}

fun CreationExtras.synapse() : Synapse = (this[AndroidViewModelFactory.APPLICATION_KEY] as Synapse)