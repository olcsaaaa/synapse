package com.olidev.synapse_mobile.data.local.dtos

data class TokenRequest(
    val idToken: String
)

data class AuthResponse(
    val token: String,
    val userId: String,
    val ExpiresAt: Long
)
