package com.olidev.synapse_mobile.ui.auth

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "button_scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 2.dp,
        label = "button_elevation"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
            ),
        shape = RoundedCornerShape(SynapseSpacing.Large),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        onClick = onClick,
        tonalElevation = elevation,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_g),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Continue with Google",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight(500),
                    letterSpacing = 0.25.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun SignInButtonPreview(){
    SynapseTheme {
        GoogleSignInButton(onClick = {})
    }
}
