package com.olidev.synapse_mobile.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.toPath
import com.olidev.synapse_mobile.ui.theme.SynapseTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingAnimation() {

    val electricViolet = SynapseTheme.extraColors.electricViolet.color
    val loadingShapes = remember {
        listOf(
            MaterialShapes.Pill,
            MaterialShapes.Pentagon,
            MaterialShapes.Square,
            MaterialShapes.Circle,
        )
    }

    val transition = rememberInfiniteTransition("cycle_morph")

    val totalProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = (loadingShapes.size - 1).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progress"
    )
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearOutSlowInEasing),
        ),
        label = "rotation"
    )

    val bounce by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2250
                0f at 0 using FastOutLinearInEasing
                1f at 1600 using LinearOutSlowInEasing
                0f at 2250 using LinearOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "boing"
    )
    val startIndex = totalProgress.toInt().coerceAtMost(loadingShapes.size - 2)
    val endIndex = startIndex + 1
    val morphProgress = totalProgress - startIndex

    val activeMorph = remember(startIndex) {
        Morph(loadingShapes[startIndex], loadingShapes[endIndex])
    }



    Box(
        modifier = Modifier
            .size(140.dp)
            .graphicsLayer {
                rotationZ = rotation
                translationY = bounce * 20.dp.toPx()

            }
            .drawWithCache {
                val path = activeMorph.toPath(progress = morphProgress).asComposePath()

                val matrix = Matrix()
                matrix.translate(size.width / 2f, size.height / 2f)


                val scaleSize = 100.dp.toPx()
                matrix.scale(scaleSize, scaleSize)

                matrix.translate(-0.5f, -0.5f)

                path.transform(matrix)

                onDrawBehind {
                    drawPath(
                        path = path,
                        color = electricViolet
                    )
                }
            }
    )
}

@Composable
@Preview
fun LoadingCookiePreview() {
    SynapseTheme {
        LoadingAnimation()
    }
}
