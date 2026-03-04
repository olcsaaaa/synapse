package com.olidev.synapse_mobile.ui.components


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.data.local.entities.Deck
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckCard(deck: Deck, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current

    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()

    val (containerColor, contentColor) = when (deck.colorSeed % 4) {
        0 -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        1 -> MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary
        2 -> MaterialTheme.colorScheme.tertiary to MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.onError
    }


    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "CardScale",
    )

    ElevatedCard(
        onClick = {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            /*TODO: Add deck navigation*/
        },
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .aspectRatio(1.5f),
        shape = RoundedCornerShape(SynapseSpacing.Large),
        interactionSource = interactionSource,
        colors = CardColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.3f),
            disabledContentColor = contentColor.copy(alpha = 0.3f),
        )

    ) {
        Column(modifier = Modifier.padding(SynapseSpacing.Large)) {
            Text(
                text = deck.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight(800)
            )
            if (deck.description.isNotBlank()) {
                Text(
                    text = deck.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}