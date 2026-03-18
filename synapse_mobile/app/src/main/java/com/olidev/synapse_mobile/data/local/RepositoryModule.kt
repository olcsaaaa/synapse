package com.olidev.synapse_mobile.data.local

import com.olidev.synapse_mobile.data.local.daos.DeckDao
import com.olidev.synapse_mobile.data.repository.AuthRepository
import com.olidev.synapse_mobile.data.repository.DeckRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDeckRepository(
        deckDao: DeckDao,
        api: SynapseApi,
    ): DeckRepository {
        return DeckRepository(deckDao, api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SynapseApi,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepository(api, sessionManager)
    }

}