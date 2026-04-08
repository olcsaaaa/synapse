package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.olidev.synapse_mobile.R

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class)
fun BouncingPlayButton(
    onClick: () -> Unit,
    isExpanded: Boolean,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current

    LaunchedEffect(isPressed) {
        if (isPressed) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    val springSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = springSpec,
        label = "fab_spring"
    )

    val fontWeight by animateFloatAsState(
        targetValue = if (isPressed) 800f else 500f,
        animationSpec = springSpec,
        label = "font_weight"
    )

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            expanded = isExpanded,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.play_arrow_24dp),
                    contentDescription = null
                )
            },
            text = {
                Text(
                    text = "Start practice",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.roboto_flex,
                                variationSettings = FontVariation.Settings(
                                    FontVariation.weight(fontWeight.toInt())
                                )
                            )
                        )
                    )
                )
            },
            containerColor = containerColor,
            contentColor = contentColor,
            shape = FloatingActionButtonDefaults.mediumExtendedFabShape,
            interactionSource = interactionSource,
            modifier = modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        )
    }
}