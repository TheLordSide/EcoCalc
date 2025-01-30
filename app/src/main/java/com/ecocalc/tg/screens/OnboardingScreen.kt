import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecocalc.tg.onboarding.OnboardingPage
import com.ecocalc.tg.onboarding.OnboardingPage.Companion.OnboardingPages
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { OnboardingPages.size })
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(true) } // Gérer la visibilité

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -100 }) // Animation de sortie
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bouton en haut à droite
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage == OnboardingPages.lastIndex) {
                            isVisible = false // Déclenche l'animation de sortie
                            scope.launch {
                                kotlinx.coroutines.delay(500) // Temps pour l'animation
                                onFinish()
                            }
                        } else {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(if (pagerState.currentPage == OnboardingPages.lastIndex) "Terminer" else "Suivant")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Suivant"
                    )
                }
            }

            // Contenu principal (Image, titre, description)
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                OnboardingPageScreen(OnboardingPages[page])
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Indicateur de pages en bas à gauche
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PageIndicator(currentPage = pagerState.currentPage, totalPages = OnboardingPages.size)
            }
        }
    }
}
@Composable
fun PageIndicator(currentPage: Int, totalPages: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacement de 8.dp entre les dots
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 12.dp else 8.dp)
                    .background(if (index == currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }
    }
}


@Composable
fun OnboardingPageScreen(page: OnboardingPage) {
    val textColor = MaterialTheme.colorScheme.onBackground // Couleur adaptée au thème
    val imageTint = MaterialTheme.colorScheme.primary // Ajuste selon ton besoin

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp), // Ajuste la taille si nécessaire
            colorFilter = ColorFilter.tint(imageTint) // Appliquer la teinte au besoin
        )
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor // Couleur du texte dynamique
        )
        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = textColor, // Adapte la couleur du texte
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
@Preview
fun OnboardingScreenPreview() {
    OnboardingScreen(onFinish = {})
}