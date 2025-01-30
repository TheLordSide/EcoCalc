package com.ecocalc.tg.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecocalc.tg.ui.theme.EcoCalcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankFeeCalculatorScreen(modifier: Modifier = Modifier) {
    listOf("Dépôt")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calcul des Frais Bancaires") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            DepositScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBankFeesScreen() {
    EcoCalcTheme {
        BankFeeCalculatorScreen()
    }
}
