package com.olidev.synapse_mobile.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.Typography

@Composable
fun SynapseLogo(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Synapse",
            style = Typography.displaySmall.copy(
                fontSize = 96.sp,
                letterSpacing = (-2).sp,
                lineHeight = 100.sp,
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Master your mind.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(top = SynapseSpacing.Small)
        )
    }
}