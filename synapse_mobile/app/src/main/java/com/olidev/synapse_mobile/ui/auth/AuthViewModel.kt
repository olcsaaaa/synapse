package com.olidev.synapse_mobile.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.olidev.synapse_mobile.data.helpers.AuthConfig
import com.olidev.synapse_mobile.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    sealed class AuthUiState{
        object Loading: AuthUiState()
        object Idle: AuthUiState()
        data class Error(val message: String): AuthUiState()
        data class Success(val token: String): AuthUiState()
    }

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    fun createGetCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(AuthConfig.GOOGLE_WEB_CLIENT_ID)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun onSignInResult(result: GetCredentialResponse) {
        uiState = AuthUiState.Loading
        try{
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            viewModelScope.launch {
                val authResult = repo.signInWithGoogle(idToken)

                authResult.onSuccess {
                    uiState = AuthUiState.Success(idToken)
                }.onFailure { error ->
                    uiState = AuthUiState.Error(error.message ?: "Unknown error")
                }
            }
        }catch (e: Exception){
            uiState = if (e is GetCredentialCancellationException) AuthUiState.Idle else AuthUiState.Error(e.message ?: "Unknown error")
        }
    }

    fun onSignInError(e: Exception){
        if(e is GetCredentialCancellationException){
            uiState = AuthUiState.Idle
        }else{
            uiState = AuthUiState.Error(e.message ?: "Unknown error")
        }
    }
}