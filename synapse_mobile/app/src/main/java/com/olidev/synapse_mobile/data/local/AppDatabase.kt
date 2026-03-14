package com.olidev.synapse_mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.local.daos.FlashcardDao
import com.olidev.synapse_mobile.data.local.daos.UserDao
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import com.olidev.synapse_mobile.data.local.entities.User

@Database(entities = [User::class, Deck::class, Flashcard::class], version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deckDao(): DeckDao
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val RoomCallback = object : Callback() {
            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO users (id, name, email, createdAt) VALUES ('TEST_ID', 'Test Pilot', 'test@synapse.com', ${System.currentTimeMillis()})")
            }

            override fun onOpen(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onOpen(db)
                db.execSQL("INSERT OR IGNORE INTO users (id, displayName, email) VALUES ('TEST_ID', 'Test Pilot', 'test@synapse.com')")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studycards_db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .addCallback(RoomCallback)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}