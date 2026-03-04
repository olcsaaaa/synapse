package com.olidev.synapse_mobile.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olidev.synapse_mobile.R
import com.olidev.synapse_mobile.ui.AppViewModelProvider
import com.olidev.synapse_mobile.ui.components.DeckCard
import com.olidev.synapse_mobile.ui.components.EmptyStateHero
import com.olidev.synapse_mobile.ui.decks.DeckViewModel
import com.olidev.synapse_mobile.ui.theme.SynapseSpacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: DeckViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val decks by viewModel.deckUiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val haptics = LocalHapticFeedback.current

    val spacing = SynapseSpacing

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        containerColor = MaterialTheme.colorScheme.surface,

        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Synapse",
                        style = MaterialTheme.typography.displaySmall,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                expanded = isExpanded,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_add_24),
                        contentDescription = "Add Deck"
                    )
                },
                text = { Text("Add Deck") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = FloatingActionButtonDefaults.extendedFabShape,

                )
        },


        ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {

            Text(
                modifier = Modifier
                    .padding(bottom = spacing.Small)
                    .padding(start = spacing.Medium)
                    .rotate(-1f),
                text = "Welcome back!",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,


            )
            if(decks.isNotEmpty()){
            Text(
                modifier = Modifier.padding(bottom = spacing.Medium),
                text = when (val count = decks.size){
                    1-> "You have one deck to master"
                    else -> "You have $count decks to master"
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight(450),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
            }


            Spacer(modifier = Modifier.height(spacing.Hero))

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(topStart = spacing.Large, spacing.Large),
                tonalElevation = 1.dp
            ) {

                if (decks.isEmpty()) {
                    EmptyStateHero()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(spacing.Large),
                        contentPadding = PaddingValues(spacing.Medium),
                        state = listState
                    ) {
                        items(decks) { deck ->
                            DeckCard(deck = deck)
                        }
                    }
                }
            }
        }
    }
}
