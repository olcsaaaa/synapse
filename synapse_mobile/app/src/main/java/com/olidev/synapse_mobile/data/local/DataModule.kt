package com.olidev.synapse_mobile.data.local

import android.content.Context
import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.repository.AuthRepository
import com.olidev.synapse_mobile.data.repository.DeckRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    @Provides
    @Singleton
    fun provideDeckDao(database: AppDatabase): DeckDao {
        return database.deckDao()
    }

}