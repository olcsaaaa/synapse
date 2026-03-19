package com.olidev.synapse_mobile.data.repository

import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.dtos.TokenRequest

class AuthRepository(private val api: SynapseApi, private val sessionManager: SessionManager) {

    suspend fun signInWithGoogle(idToken: String): Result<Boolean> {
        return try {
//            val response = api.verifyGoogleToken(TokenRequest(idToken))
//
//            if (response.isSuccessful) {
//                val authData = response.body()?: return Result.failure(Exception("Empty body"))
//
//                sessionManager.saveSession(
//                    token = authData.token,
//                    userId = authData.userId
//                )
//                Result.success(true)
//            } else {
//                Result.failure(Exception("Server error: ${response.code()}"))
//            }
            //TODO: When backend is ready revert to that
            kotlinx.coroutines.delay(2000)
            sessionManager.saveSession("TEST_TOKEN", "TEST_USER_ID")
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}