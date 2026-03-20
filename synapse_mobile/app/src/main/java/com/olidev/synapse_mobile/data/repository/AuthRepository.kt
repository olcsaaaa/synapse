package com.olidev.synapse_mobile.data.repository

import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.local.daos.UserDao
import com.olidev.synapse_mobile.data.local.dtos.TokenRequest
import com.olidev.synapse_mobile.data.local.entities.User
import javax.inject.Inject

class AuthRepository@Inject constructor(
    private val api: SynapseApi,
    private val sessionManager: SessionManager,
    private val userDao: UserDao
    ) {
    suspend fun signInWithGoogle(idToken: String, displayName: String): Result<Boolean> {
        return try {
            val response = api.verifyGoogleToken(TokenRequest(idToken))

            if (response.isSuccessful) {
                val authData = response.body()?: return Result.failure(Exception("Empty body"))

                sessionManager.saveSession(
                    token = authData.token,
                    userId = authData.userId
                )

                val localUser = User(
                    id = authData.userId,
                    displayName = displayName,
                    email = "",
                    authProvider = "Google"
                )
                userDao.insertUser(localUser)
                Result.success(true)
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}