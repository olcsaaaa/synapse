package com.olidev.synapse_mobile.data.local.dtos

import com.olidev.synapse_mobile.data.local.entities.Deck

data class DeckDto(
    val id: String,
    val name: String,
    val description: String,
    val colorSeed: Int,
    val lastModified: Long
    )

fun DeckDto.toEntity(isSynced: Boolean, ownerId: String) : Deck = Deck(
    id = id,
    name = name,
    description = description,
    colorSeed = colorSeed,
    lastModified = lastModified,
    isSynced = isSynced,
    ownerId = ownerId
)