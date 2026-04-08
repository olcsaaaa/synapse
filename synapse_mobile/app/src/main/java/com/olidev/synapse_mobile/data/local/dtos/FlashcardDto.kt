package com.olidev.synapse_mobile.data.local.dtos

import com.olidev.synapse_mobile.data.local.entities.Flashcard

data class FlashcardDto(
    val id: String,
    val front: String,
    val back: String,
    val deckId: String,
    val isSynced: Boolean,
    val lastModified: Long
)

fun FlashcardDto.toEntity(isSynced: Boolean = true) = Flashcard(
    id = this.id,
    front = this.front,
    back = this.back,
    deckId = this.deckId,
    isSynced = isSynced,
    lastModified = this.lastModified,
    isDeleted = false
)

fun Flashcard.toDto(): FlashcardDto {
    return FlashcardDto(
        id = this.id,
        front = this.front,
        back = this.back,
        deckId = this.deckId,
        isSynced = this.isSynced,
        lastModified = this.lastModified
    )
}
