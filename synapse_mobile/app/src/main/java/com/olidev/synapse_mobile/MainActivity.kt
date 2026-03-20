package com.olidev.synapse_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.olidev.synapse_mobile.ui.AuthState
import com.olidev.synapse_mobile.ui.MainViewModel
import com.olidev.synapse_mobile.ui.auth.AuthScreen
import com.olidev.synapse_mobile.ui.auth.SynapseLogo
import com.olidev.synapse_mobile.ui.deck_details.DeckDetailsScreen
import com.olidev.synapse_mobile.ui.home.HomeScreen
import com.olidev.synapse_mobile.ui.theme.SynapseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalMaterial3ExpressiveApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSize = calculateWindowSizeClass(this)

            val mainViewModel: MainViewModel = hiltViewModel()

            val authState by mainViewModel.authState.collectAsStateWithLifecycle()

            val navController = rememberNavController()

            SynapseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (authState) {
                        is AuthState.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                SynapseLogo()
                            }
                        }

                        else -> {
                            val startDestination =
                                if (authState is AuthState.Authenticated) "home" else "auth"
                            NavHost(
                                navController = navController,
                                startDestination = startDestination
                            ) {
                                composable("auth") {
                                    AuthScreen(
                                        onAuthComplete = {
                                            navController.navigate("home") {
                                                popUpTo("auth") { inclusive = true }
                                            }
                                        }
                                    )
                                }
                                composable("home") {
                                    HomeScreen(
                                        windowSize = windowSize,
                                        onDeckClick = { deckId ->
                                            navController.navigate("deck_details/$deckId")
                                        }
                                    )
                                }
                                composable(
                                    route = "deck_details/{deckId}", arguments = listOf(
                                        androidx.navigation.navArgument("deckId") {
                                            type = androidx.navigation.NavType.StringType
                                        }
                                    )
                                ) {
                                    DeckDetailsScreen(
                                        onNavigateBack = {navController.popBackStack()},
                                        onStartPracticing = {
                                        }
                                    )
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}