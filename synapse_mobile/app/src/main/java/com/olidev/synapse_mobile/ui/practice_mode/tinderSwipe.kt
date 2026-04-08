package com.olidev.synapse_mobile.ui.practice

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

fun Modifier.tinderSwipe(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onDragProgress: (Float) -> Unit
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(offsetX.value) {
        val progress = (offsetX.value / 400f).coerceIn(-1f, 1f)
        onDragProgress(progress)
    }

    pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                scope.launch {
                    val swipeThreshold = size.width / 3f

                    if (offsetX.value > swipeThreshold) {
                        offsetX.animateTo(size.width.toFloat() * 2, tween(300))
                        onSwipeRight()
                    } else if (offsetX.value < -swipeThreshold) {
                        offsetX.animateTo(-size.width.toFloat() * 2, tween(300))
                        onSwipeLeft()
                    } else {
                        launch { offsetX.animateTo(0f, spring(stiffness = 400f)) }
                        launch { offsetY.animateTo(0f, spring(stiffness = 400f)) }
                    }
                }
            },
            onDrag = { change, dragAmount ->
                change.consume()
                scope.launch {
                    offsetX.snapTo(offsetX.value + dragAmount.x)
                    offsetY.snapTo(offsetY.value + dragAmount.y)
                }
            }
        )
    }
        .graphicsLayer {
            translationX = offsetX.value
            translationY = offsetY.value
            rotationZ = (offsetX.value / 40f).coerceIn(-15f, 15f)
        }
}