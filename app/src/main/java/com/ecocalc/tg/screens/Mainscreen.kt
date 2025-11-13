package com.ecocalc.tg.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ecocalc.tg.navigation.BottomNavItem
import com.ecocalc.tg.navigation.BottomNavigationBar

@Composable
fun MainScreen(){

    var selectedScreen by remember { mutableStateOf<BottomNavItem>(BottomNavItem.MobileMoney) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedScreen = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { innerPadding ->

        when (selectedScreen) {
            is BottomNavItem.MobileMoney -> MobileMoneyCalculatorScreen()
            is BottomNavItem.BankFees -> BankFeeCalculatorScreen()
            is BottomNavItem.Withdrawal -> WithdrawalScreen()
            is BottomNavItem.History -> AboutAppScreen(modifier = Modifier.padding(innerPadding))
        }

    }
}