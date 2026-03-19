package com.olidev.synapse_mobile.ui.auth


import android.credentials.GetCredentialException
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ListenableWorker
import com.olidev.synapse_mobile.ui.components.LoadingAnimation
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import kotlinx.coroutines.launch


@ExperimentalMaterial3ExpressiveApi
@Composable
fun AuthScreen(
    onAuthComplete: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val spacing = SynapseSpacing
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthViewModel.AuthUiState.Success -> {
                onAuthComplete()
            }

            is AuthViewModel.AuthUiState.Error -> {
                snackbarHostState.showSnackbar(uiState.message)
            }

            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
        ) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "auth_state"
            ) { state ->
                if (state is AuthViewModel.AuthUiState.Loading) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                } else {
                    AuthContent(
                        onSignInClick = {
                            scope.launch {
                                try {
                                    val request = viewModel.createGetCredentialRequest()
                                    val result = credentialManager.getCredential(
                                        request = request,
                                        context = context
                                    )
                                    viewModel.onSignInResult(result)
                                } catch (e: GetCredentialCancellationException) {
                                } catch (e: NoCredentialException) {
                                    snackbarHostState.showSnackbar("No credentials found")
                                } catch (e: Exception) {
                                    viewModel.onSignInError(e)
                                    snackbarHostState.showSnackbar("Error: ${e.message}")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
