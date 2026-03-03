package com.olidev.synapse_mobile

import android.app.Application
import com.olidev.synapse_mobile.data.local.AppDatabase
import com.olidev.synapse_mobile.data.repository.DeckRepository
import com.olidev.synapse_mobile.data.repository.FlashcardRepository
import com.olidev.synapse_mobile.data.repository.UserRepository

class Synapse : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    val userRepo by lazy { UserRepository(database.userDao()) }
    val deckRepository by lazy { DeckRepository(database.deckDao()) }
    val flashcardRepository by lazy { FlashcardRepository(database.flashcardDao()) }

}