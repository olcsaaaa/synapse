package com.olidev.synapse_mobile.ui.home

import androidx.room.Embedded
import com.olidev.synapse_mobile.data.local.entities.Deck

data class DeckWithCount(
    @Embedded val deck: Deck,
    val cardCount: Int
)
