package com.olidev.synapse_mobile.ui.components


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.data.local.entities.Deck

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckCard(deck: Deck){
    val haptics = LocalHapticFeedback.current

    val interactionSource = remember {MutableInteractionSource()}

    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "CardScale",
    )

    ElevatedCard(
        onClick = {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = RoundedCornerShape(28.dp),
        interactionSource = interactionSource
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = deck.name, style = MaterialTheme.typography.titleLarge)
            Text(text = deck.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        }
    }
}