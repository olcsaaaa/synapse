package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.components.FloatingActionRow
import com.olidev.synapse_mobile.ui.components.LoadingAnimation
import com.olidev.synapse_mobile.ui.theme.ColorFamily
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import com.olidev.synapse_mobile.ui.theme.Typography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DeckDetailsScreen(
    onNavigateBack: () -> Unit,
    onStartPracticing: () -> Unit,
    viewModel: DeckDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = SynapseSpacing

    val extra = SynapseTheme.extraColors
    val colors = MaterialTheme.colorScheme


    val colorPairings = remember(colors, extra) {
        listOf(
            ColorFamily(
                colors.primary,
                colors.onPrimary,
                colors.primaryContainer,
                colors.onPrimaryContainer
            ),
            ColorFamily(
                colors.secondary,
                colors.onSecondary,
                colors.secondaryContainer,
                colors.onSecondaryContainer
            ),
            ColorFamily(
                colors.tertiary,
                colors.onTertiary,
                colors.tertiaryContainer,
                colors.onTertiaryContainer
            ),
            extra.vividMagenta,
            extra.springGreen,
            extra.electricViolet
        )
    }

    val selectedColorPairing = colorPairings[uiState.deck?.colorSeed ?: 0]


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {

            if (uiState.flashcards.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = onStartPracticing,
                    containerColor = selectedColorPairing.colorContainer,
                    contentColor = selectedColorPairing.onColor,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .padding(bottom = spacing.Medium)
                        .padding(end = spacing.Medium)
                ) {
                    Icon(painterResource(R.drawable.play_arrow_24dp), null)
                    Spacer(modifier = Modifier.width(spacing.Small))
                    Text(
                        text = "Start Practicing",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FloatingActionRow(onNavigateBack = onNavigateBack, isMoreShown = true)

            Text(
                text = uiState.deck?.name ?: "Loading...",
                style = Typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp)
            )

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                if (uiState.isLoading) {
                    LoadingAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        color = selectedColorPairing.color
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .padding(horizontal = spacing.Large),
                        verticalArrangement = Arrangement.spacedBy(spacing.Medium)
                    ) {
                        item {
                            AddCardListItem(
                                accentColor = selectedColorPairing.color,
                                onClick = { viewModel.toggleAddCardSheet(true) },
                                modifier = Modifier.padding(top = spacing.Small)
                            )
                        }
                        if (uiState.flashcards.isEmpty()) {
                            item {

                                EmptyCardsView(modifier = Modifier.fillParentMaxHeight(0.8f))
                            }
                        } else {
                            items(uiState.flashcards, key = { it.id }) { card ->
                                FlashCardListItem(
                                    front = card.front,
                                    back = card.back,
                                    onEdit = { /* TODO */ }
                                )
                            }
                        }

                        item { Spacer(modifier = Modifier.height(120.dp)) }
                    }
                }
            }


            if (viewModel.isAddCardSheetVisible) {
                AddFlashCardSheet(
                    onDismiss = { viewModel.toggleAddCardSheet(false) },
                    onSave = { front, back -> viewModel.addCard(front, back) },
                    colorSeed = uiState.deck?.colorSeed ?: 0
                )
            }
        }
    }
}

@Composable
@Preview
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun DeckDetailsScreenPreview() {
    SynapseTheme() {
        DeckDetailsScreen(onNavigateBack = {}, onStartPracticing = {})
    }

}