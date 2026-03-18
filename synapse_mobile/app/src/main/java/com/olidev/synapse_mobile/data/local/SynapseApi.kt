package com.olidev.synapse_mobile.data.local

import com.olidev.synapse_mobile.data.local.dtos.AuthResponse
import com.olidev.synapse_mobile.data.local.dtos.TokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SynapseApi {

    @POST("auth/google")
    suspend fun verifyGoogleToken(@Body request: TokenRequest): Response<AuthResponse>
}
