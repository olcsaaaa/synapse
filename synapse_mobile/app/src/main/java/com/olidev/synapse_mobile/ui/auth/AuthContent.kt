package com.olidev.synapse_mobile.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import kotlinx.coroutines.launch

@Composable
fun AuthContent(
    onSignInClick: ()-> Unit
){
    val spacing = SynapseSpacing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SynapseLogo()
        Spacer(modifier = Modifier.height(120.dp))
        GoogleSignInButton(
            onClick = {
                onSignInClick()
            },
            modifier = Modifier.widthIn(max = 400.dp, min = 128.dp)
        )
    }
}