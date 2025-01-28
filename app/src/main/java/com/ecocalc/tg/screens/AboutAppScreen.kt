package com.ecocalc.tg.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(modifier: Modifier = Modifier) {
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
                .padding(16.dp)
        ) {


            // Description de l'application
            Text(
                text = "Cette application est actuellement en version alpha. " +
                        "Elle vise à permettre des tests et à recueillir des retours avant une version finale.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Note sur l'objectif des tests
            Text(
                text = "Merci de signaler tout problème ou suggestion afin de contribuer à son amélioration.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
