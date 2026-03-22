package com.olidev.synapse_mobile.ui.deck_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button


import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.olidev.synapse_mobile.data.local.entities.Flashcard
import com.olidev.synapse_mobile.ui.components.DeckFormDialog
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

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showEditDeckDialog by remember { mutableStateOf(false) }

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
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        floatingActionButton = {

            if (uiState.flashcards.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = onStartPracticing,
                    containerColor = selectedColorPairing.color,
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
            FloatingActionRow(
                onNavigateBack = onNavigateBack,
                isMoreShown = true,
                onEditClick = { showEditDeckDialog = true },
                onDeleteClick = { showDeleteConfirm = true }
            )

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
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier
                            .widthIn(max = 1000.dp)
                            .padding(horizontal = spacing.Large),
                        verticalItemSpacing = spacing.Medium,
                        horizontalArrangement = Arrangement.spacedBy(spacing.Medium),
                        columns = StaggeredGridCells.Fixed(2),
                    ) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            AddCardListItem(
                                accentColor = selectedColorPairing.color,
                                onClick = { viewModel.toggleAddCardSheet(true) },
                                modifier = Modifier.padding(top = spacing.Small)
                            )
                        }
                        if (uiState.flashcards.isEmpty()) {
                            item (span = StaggeredGridItemSpan.FullLine) {
                                EmptyCardsView(
                                    modifier = Modifier.heightIn(min = 400.dp),
                                    color = selectedColorPairing.color,
                                )
                            }
                        } else {
                            items(uiState.flashcards, key = { it.id }) { card ->
                                FlashCardListItem(
                                    front = card.front,
                                    back = card.back,
                                    onEdit = { viewModel.editCard(card.front, card.back) },
                                    onDelete = { viewModel.deleteCard(card) },
                                    color = selectedColorPairing.color
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

            if (showEditDeckDialog) {

                DeckFormDialog(
                    initialName = uiState.deck?.name ?: "",
                    initialDescription = uiState.deck?.description ?: "",
                    initialColorSeed = uiState.deck?.colorSeed ?: 0,
                    isEditing = true,
                    onDismiss = { showEditDeckDialog =false },
                    onSave = { name, desc, seed ->
                        viewModel.updateDeck(name, desc, seed)
                        showEditDeckDialog = false
                    }
                )
            }


            if (showDeleteConfirm) {
                DeleteDeckConfirmation(
                    deckName = uiState.deck?.name ?: "this deck",
                    onConfirm = {
                        viewModel.deleteDeck(onComplete = onNavigateBack)
                        showDeleteConfirm = false
                    },
                    onDismiss = { showDeleteConfirm = false }
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