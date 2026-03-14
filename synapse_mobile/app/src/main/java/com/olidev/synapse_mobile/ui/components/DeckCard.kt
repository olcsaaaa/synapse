package com.olidev.synapse_mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme

@Composable
fun DeckCard(deck: Deck, modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    val extra = SynapseTheme.extraColors


    val accentColor = when (deck.colorSeed % 6) {
        0 -> colorScheme.primary
        1 -> colorScheme.secondary
        2 -> colorScheme.tertiary
        3 -> extra.vividMagenta.color
        4 -> extra.springGreen.color
        else -> extra.electricViolet.color
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.7f)
            .background(color = accentColor.copy(0.4f), shape = RoundedCornerShape(SynapseSpacing.Large))
            .border(BorderStroke(2.dp, accentColor.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(SynapseSpacing.Large)),
        shape = RoundedCornerShape(SynapseSpacing.Large),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(SynapseSpacing.Medium)) {
            Text(
                text = deck.name,
                style = MaterialTheme.typography.titleLarge,
                color = accentColor,
                fontWeight = FontWeight(900),
                maxLines = 1
            )

            if (deck.description.isNotBlank()) {
                Text(
                    text = deck.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "12 Cards",
                style = MaterialTheme.typography.labelSmall,
                color = accentColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold
            )
        }
    }
}