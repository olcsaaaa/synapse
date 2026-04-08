package com.olidev.synapse_mobile.ui.home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.lazy.grid.LazyGridState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.R.font.roboto_flex

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class)
fun BouncingAddButton(gridState: LazyGridState, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current
    val isExpanded by remember { derivedStateOf { gridState.firstVisibleItemIndex == 0 } }


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

    val fontWeight by animateFloatAsState(if (isPressed) 800f else 500f, animationSpec = springSpec)

    CompositionLocalProvider(LocalRippleConfiguration provides null) {

        ExtendedFloatingActionButton(
            onClick = {
                onClick()
            },
            expanded = isExpanded,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.round_add_24),
                    contentDescription = "Add Deck"
                )
            },
            text = {
                Text(
                    text = "Add Deck",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = FontFamily(
                            Font(
                                resId = roboto_flex,
                                variationSettings = FontVariation.Settings(
                                    FontVariation.weight(fontWeight.toInt())
                                )
                            )
                        )
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = FloatingActionButtonDefaults.mediumExtendedFabShape,
            interactionSource = interactionSource,
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        )
    }
}
