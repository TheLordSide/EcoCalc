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
fun MobileMoneyCalculatorScreen() {
    var currentTab by remember { mutableIntStateOf(0) } // 0 pour dépôt, 1 pour retrait
    val tabs = listOf("Dépôt", "Retrait")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calcul des Frais Mobile Money") },
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
            // Tabs
            TabRow(selectedTabIndex = currentTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = currentTab == index,
                        onClick = { currentTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage du contenu en fonction de l'onglet sélectionné
            when (currentTab) {
                0 -> MobileMoneyDepositScreen()
                1 -> MobileMoneyWithdrawalScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMobileMoneyCalculatorScreen() {
    EcoCalcTheme {
        MobileMoneyCalculatorScreen()
    }
}
