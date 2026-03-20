package com.olidev.synapse_mobile.data.local

import com.olidev.synapse_mobile.data.local.dtos.AuthResponse
import com.olidev.synapse_mobile.data.local.dtos.DeckDto
import com.olidev.synapse_mobile.data.local.dtos.FlashcardDto
import com.olidev.synapse_mobile.data.local.dtos.TokenRequest
import com.olidev.synapse_mobile.data.local.entities.Deck
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SynapseApi {

    @POST("api/auth/google")
    suspend fun verifyGoogleToken(@Body request: TokenRequest): Response<AuthResponse>

    @POST("api/decks/sync")
    suspend fun syncDecks(@Body decks: List<Deck>): Response<Unit>

    @GET("api/decks")
    suspend fun getAllDecks(userId: String): Response<List<DeckDto>>

    @POST("api/decks/update")
    suspend fun updateDeck(deck: Deck): Response<DeckDto>

    @DELETE("api/decks/delete/{id}")
    suspend fun deleteDeck(@Path("id") id: String): Response<Unit>

    @GET("api/flashcards")
    suspend fun getAllFlashcards(): Response<List<FlashcardDto>>

    @GET("api/flashcards/{deckId}")
    suspend fun getCardsByDeck(@Path("deckId") deckId: String): Response<List<FlashcardDto>>

    @POST("api/flashcards/sync")
    suspend fun syncFlashcards(@Body flashcards: List<FlashcardDto>): Response<Unit>

    @DELETE("api/flashcards/{id}")
    suspend fun deleteFlashcard(@Path("id") id: String): Response<Unit>


}
