package com.ecocalc.tg.navigation

import com.ecocalc.tg.screens.OnboardingScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecocalc.tg.screens.MainScreen
import com.ecocalc.tg.utils.isOnboardingCompleted
import com.ecocalc.tg.utils.saveOnboardingCompleted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var hasSeenOnboarding by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var showContent by remember { mutableStateOf(false) }

    // Charger l'état de l'onboarding en mode asynchrone
    LaunchedEffect(Unit) {
        delay(2000)
        hasSeenOnboarding = isOnboardingCompleted(context)
        isLoading = false

        delay(300)
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Fond constant
        contentAlignment = Alignment.Center
    ) {
        // Animation entre le chargement et la navigation
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -100 })
        ) {
            LoadingScreen()
        }

        AnimatedVisibility(
            visible = !isLoading && showContent,
            enter = fadeIn() + slideInHorizontally(initialOffsetX = { 100 })
        ) {
            NavHost(
                navController,
                startDestination = if (hasSeenOnboarding) "main" else "onboarding"
            ) {
                composable("onboarding") {
                    OnboardingScreen {
                        scope.launch {
                            saveOnboardingCompleted(context)
                            navController.navigate("main") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                }
                composable("main") { MainScreen() }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Assurer le même fond
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
