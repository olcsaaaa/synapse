package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EmptyStateHero() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(modifier = Modifier
            .size(180.dp)
            .rotate(-15f)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraExtraLarge
            ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .rotate(15f),
                painter = painterResource(id = R.drawable.person_with_question_24dp),
                contentDescription = "No decks yet...",
                tint = MaterialTheme.colorScheme.surfaceContainerLowest,
            )
        }


        Spacer(Modifier.height(SynapseSpacing.Large))

        Text(
            text = "Ready to learn?",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().rotate(-2f),
            fontWeight = FontWeight(700)
        )
//
        Text(
            text = "Your library is a blank canvas.\nTap below to start building.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = SynapseSpacing.Small)
        )
    }
}