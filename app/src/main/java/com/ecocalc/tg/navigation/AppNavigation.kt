import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
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
import com.ecocalc.tg.screens.OnboardingScreen
import com.ecocalc.tg.utils.isOnboardingCompleted
import com.ecocalc.tg.utils.saveOnboardingCompleted
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var hasSeenOnboarding by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) } // État de chargement

    // Charger l'état de l'onboarding en mode asynchrone
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        hasSeenOnboarding = isOnboardingCompleted(context)
        isLoading = false // Une fois l'état de l'onboarding chargé, désactiver le chargement
    }

    // Afficher un écran de chargement temporaire pendant le processus de chargement
    if (isLoading) {
        LoadingScreen() // Affiche un écran neutre pendant le chargement
    } else {
        // Une fois l'état chargé, choisir le bon écran en fonction de hasSeenOnboarding
        NavHost(
            navController,
            startDestination = if (hasSeenOnboarding) "main" else "onboarding"
        ) {
            composable("onboarding") {
                OnboardingScreen {
                    scope.launch {
                        saveOnboardingCompleted(context)
                        navController.navigate("main") {
                            popUpTo("onboarding") { inclusive = true } // Supprime l'écran d'onboarding de la pile
                        }
                    }
                }
            }
            composable("main") { MainScreen() }
        }
    }
}

@Composable
fun LoadingScreen() {
    // Un écran de chargement pour éviter tout flash d'écran
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator() // Indicateur de chargement
    }
}
