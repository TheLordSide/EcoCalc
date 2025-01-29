package com.ecocalc.tg.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ecocalc.tg.onboarding.OnboardingPage
import com.ecocalc.tg.onboarding.OnboardingPage.Companion.OnboardingPages
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { OnboardingPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            OnboardingPageScreen(OnboardingPages[page])
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Indicateurs de pages
        PageIndicator(currentPage = pagerState.currentPage, totalPages = OnboardingPages.size)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (pagerState.currentPage == OnboardingPages.lastIndex) {
                    onFinish()  // Rediriger vers l’écran principal
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            }
        ) {
        Text(if (pagerState.currentPage == OnboardingPages.lastIndex) "Terminer" else "Suivant")
        }
    }
}


@Composable
fun PageIndicator(currentPage: Int, totalPages: Int) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 12.dp else 8.dp)
                    .background(if (index == currentPage) Color.Black else Color.Gray, CircleShape)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun OnboardingPageScreen(page: OnboardingPage){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(page.image), contentDescription = null)
        Text(text = page.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = page.description, fontSize = 16.sp, textAlign = TextAlign.Center)
    }

}

@Composable
@Preview
fun OnboardingScreenPreview() {
    OnboardingScreen(onFinish = {})
}
