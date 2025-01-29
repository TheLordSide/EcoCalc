package com.ecocalc.tg.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ecocalc.tg.R

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Ajouter l'image
        Image(
            painter = painterResource(id = R.drawable.logo_light),  // Remplace avec l'ID de ton image
            contentDescription = "Splash Screen",
            modifier = Modifier.size(200.dp)  // Ajuste la taille de l'image selon tes besoins
        )
    }

}
