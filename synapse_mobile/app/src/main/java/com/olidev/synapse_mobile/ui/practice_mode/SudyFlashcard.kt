package com.olidev.synapse_mobile.ui.practice_mode

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import com.olidev.synapse_mobile.ui.theme.Typography

@Composable
fun StudyFlashcard(
    frontText: String,
    backText: String,
    isFlipped : Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "card_rotation"
    )

    val screenDensity = LocalDensity.current.density



    Surface(
        modifier = modifier
            .graphicsLayer(
                rotationY = rotation,
                cameraDistance = 12f * screenDensity,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onFlip()
            },

        shape = RoundedCornerShape(SynapseSpacing.Large),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shadowElevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SynapseSpacing.ExtraLarge),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90) {
                Text(
                    text = frontText,
                    style = Typography.displaySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = backText,
                    style = Typography.displaySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.graphicsLayer(rotationY = 180f)
                )
            }
        }


    }

}

@Preview
@Composable
fun StudyFlashcardPreview(){
    SynapseTheme{
        StudyFlashcard(
            "The question is this",
            "The answer is this",
            isFlipped = false,
            onFlip = {}
        )
    }
}