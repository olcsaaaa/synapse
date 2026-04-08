package com.olidev.synapse_mobile.data.local.dtos

data class SynapseUser(
    val id: String,
    val email: String,
    val displayName: String,
    val avatarUrl: String?
)
