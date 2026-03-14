package com.olidev.synapse_mobile.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.data.helpers.ColorEnergy.*
import com.olidev.synapse_mobile.data.helpers.ColorPairing
import com.olidev.synapse_mobile.ui.theme.ColorFamily


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun VibeToken(
    modifier: Modifier = Modifier,
    pairing: ColorFamily,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val polygon = MaterialShapes.Cookie9Sided

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "blob_scale"
    )

    val elevation by animateDpAsState(if (isSelected) 12.dp else 0.dp, label = "blob_elevation")
    val rotation by animateFloatAsState(if (isSelected) -5f else 1f, label = "blob_rotation")


    Surface(
        modifier = modifier
            .size(64.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .clickable(
                onClick = onClick,
                role = Role.RadioButton,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }),
        shape = polygon.toShape(),
        tonalElevation = elevation,
        color = pairing.color,
        border = BorderStroke(2.dp,pairing.color.copy(alpha = 0.15f))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,

        ) {
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.circle_empty_24dp),
                    contentDescription = "Selected",
                    tint = colorScheme.background,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

    }
}