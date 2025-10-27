package com.ecocalc.tg.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecocalc.tg.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(modifier: Modifier = Modifier) {

    val logoPainter = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.logo_dark) // Image pour le thème sombre
    } else {
        painterResource(id = R.drawable.logo_light) // Image pour le thème clair
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("À propos") },
                colors = TopAppBarDefaults.topAppBarColors()

            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Description de l'application
            Image(
                painter = logoPainter,
                contentDescription = "Logo de l'application",
                modifier = Modifier
                    .size(200.dp),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.FillWidth,
            )
            HorizontalDivider(
                modifier = Modifier.padding(8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            // Version de l'application
            Text(
                text = "Version",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
            Text(
                text = "0.1.0",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

            Text(
                text = "Sources",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
            Text(
                text = "Moov Money \nOraBank",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )

        }
    }
}


@Preview
@Composable

fun AboutScreenPreview() {
    AboutAppScreen()
}

