package com.olidev.synapse_mobile.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olidev.synapse_mobile.ui.components.AddDeckDialog
import com.olidev.synapse_mobile.ui.components.DeckCard
import com.olidev.synapse_mobile.ui.components.EmptyStateHero
import com.olidev.synapse_mobile.ui.decks.DeckViewModel
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing
import com.olidev.synapse_mobile.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class)
@Composable
fun HomeScreen(
    viewModel: DeckViewModel = hiltViewModel(),
    windowSize: WindowSizeClass,
    onDeckClick: (String) -> Unit
) {
    val decks by viewModel.deckUiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    val widthClass = windowSize.widthSizeClass
    val isTablet = widthClass != WindowWidthSizeClass.Compact
    val colCount = when (widthClass) {
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 4
        else -> 1
    }

    val spacing = SynapseSpacing
    val gridState = rememberLazyGridState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        floatingActionButton = {
            BouncingAddButton(onClick = { showAddDialog = true }, gridState = gridState)
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
        ) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(colCount),
                verticalArrangement = Arrangement.spacedBy(spacing.Hero),
                horizontalArrangement = Arrangement.spacedBy(spacing.ExtraLarge),
                contentPadding = PaddingValues(
                    top = spacing.Hero,
                    start = spacing.ExtraLarge,
                    end = spacing.ExtraLarge,
                    bottom = 120.dp
                ),
                modifier = Modifier.fillMaxSize(),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = spacing.Large)
                    ) {
                        Text(
                            text = "Synapse",
                            style = Typography.displaySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        )

                        Text(
                            modifier = Modifier.rotate(-1f),
                            text = "Welcome back!",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight(950),
                            color = MaterialTheme.colorScheme.primary,
                        )

                        if(!decks.isEmpty()){
                        Text(
                            text = "You have ${if(decks.size == 1) "one deck" else "${decks.size} decks" } to master",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        }


                    }
                }
                if (decks.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        EmptyStateHero()
                    }
                } else {
                    items(decks, key = { it.deck.id }) { item ->
                        val rotation = remember(item.deck.id) { ((Math.random() - 0.5f) * 8).toFloat() }
                        DeckCard(
                            item = item,
                            modifier = Modifier.rotate(rotation),
                            onClick = { onDeckClick(item.deck.id) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            BasicAlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
            ) {
                AddDeckDialog(
                    onDismiss = { showAddDialog = false },
                    onSave = { name, desc, seed ->
                        viewModel.addDeck(name, description = desc, seed)
                        showAddDialog = false
                    }

                )
            }
        }
    }
}
