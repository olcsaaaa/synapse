package com.olidev.synanapse_mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.olidev.synanapse_mobile.data.local.daos.DeckDao
import com.olidev.synanapse_mobile.data.local.daos.FlashcardDao
import com.olidev.synanapse_mobile.data.local.daos.UserDao
import com.olidev.synanapse_mobile.data.local.entities.Deck
import com.olidev.synanapse_mobile.data.local.entities.Flashcard
import com.olidev.synanapse_mobile.data.local.entities.User

@Database(entities = [User::class, Deck::class, Flashcard::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deckDao(): DeckDao
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studycards_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }


        }
    }
}