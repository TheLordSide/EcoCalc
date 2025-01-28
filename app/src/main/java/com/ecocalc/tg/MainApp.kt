package com.ecocalc.tg

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ecocalc.tg.navigation.BottomNavItem
import com.ecocalc.tg.navigation.BottomNavigationBar
import com.ecocalc.tg.screens.BankFeeCalculatorScreen
import com.ecocalc.tg.screens.MobileMoneyCalculatorScreen
import com.ecocalc.tg.screens.AboutAppScreen
@Composable
fun MainApp() {
   // var isOnboardingCompleted by remember { mutableStateOf(false) }
    var selectedScreen by remember { mutableStateOf<BottomNavItem>(BottomNavItem.MobileMoney) }

    Scaffold(
        bottomBar = {
                BottomNavigationBar(selectedScreen = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { innerPadding ->

            when (selectedScreen) {
                is BottomNavItem.MobileMoney -> MobileMoneyCalculatorScreen(modifier = Modifier.padding(innerPadding))
                is BottomNavItem.BankFees -> BankFeeCalculatorScreen(modifier = Modifier.padding(innerPadding))
                is BottomNavItem.History -> AboutAppScreen(modifier = Modifier.padding(innerPadding))
            }

    }
}
