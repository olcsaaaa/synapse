package com.olidev.synapse_mobile.ui.practice_mode

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olidev.synapse_mobile.ui.practice.PracticeViewModel
import com.olidev.synapse_mobile.ui.practice.tinderSwipe
import com.olidev.synapse_mobile.ui.theme.ColorFamily
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import com.olidev.synapse_mobile.ui.theme.Typography
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    deckId: String,
    onNavigateBack: () -> Unit,
    viewModel: PracticeViewModel = hiltViewModel(),
) {
    LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val cards = uiState.activeCards

    var swipeProgress by remember { mutableFloatStateOf(0f) }
    var topCardFlipped by remember { mutableStateOf(false) }


    LaunchedEffect(deckId) {
        viewModel.loadDeck(deckId)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val effectiveSwipeProgress = if (cards.isEmpty()) 0f else swipeProgress

            Canvas(modifier = Modifier.fillMaxSize()) {
                val glowRadius = size.height * 0.6f
                val rightAlpha = max(0f, effectiveSwipeProgress)
                val leftAlpha = max(0f, -effectiveSwipeProgress)

                if (leftAlpha > 0f) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Red.copy(alpha = leftAlpha * 0.25f),
                                Color.Transparent
                            ),
                            center = Offset(0f, size.height / 2),
                            radius = glowRadius
                        ),
                        radius = glowRadius,
                        center = Offset(0f, size.height / 2)
                    )
                }
                if (rightAlpha > 0f) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Green.copy(alpha = rightAlpha * 0.25f),
                                Color.Transparent
                            ),
                            center = Offset(size.width, size.height / 2),
                            radius = glowRadius
                        ),
                        radius = glowRadius,
                        center = Offset(size.width, size.height / 2)
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SynapseSpacing.ExtraLarge),
                contentAlignment = Alignment.Center
            ) {
                if (cards.isEmpty()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Deck Complete!",
                            style = Typography.displaySmall,
                            fontSize = 40.sp,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Button(
                            onClick = onNavigateBack,
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = ButtonColors(
                                containerColor = SynapseTheme.extraColors.electricViolet.color,
                                contentColor = SynapseTheme.extraColors.electricViolet.onColor,
                                disabledContainerColor = SynapseTheme.extraColors.electricViolet.color.copy(alpha = 0.5f),
                                disabledContentColor = SynapseTheme.extraColors.electricViolet.onColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Text("Go Back to Deck", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                } else {
                    cards.take(2).reversed().forEachIndexed { index, card ->
                        val isTopCard = index == 1 || cards.size == 1
                        key(card.id) {
                            StudyFlashcard(
                                frontText = card.front,
                                backText = card.back,
                                isFlipped = if (isTopCard) topCardFlipped else false,
                                onFlip = {
                                    if (isTopCard) topCardFlipped = !topCardFlipped
                                },
                                modifier = Modifier
                                    .fillMaxSize(0.5f)
                                    .padding(vertical = 32.dp)
                                    .then(
                                        if (isTopCard) {
                                            Modifier.tinderSwipe(
                                                onDragProgress = { progress: Float ->
                                                    swipeProgress = progress
                                                },
                                                onSwipeLeft = {
                                                    swipeProgress = 0f
                                                    topCardFlipped = false
                                                    viewModel.processSwipe(card, false)
                                                },
                                                onSwipeRight = {
                                                    swipeProgress = 0f
                                                    topCardFlipped = false
                                                    viewModel.processSwipe(card, true)
                                                }
                                            )
                                        } else Modifier
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PracticeScreenPreview() {
    SynapseTheme {
        PracticeScreen(onNavigateBack = {}, deckId = "1")
    }
}