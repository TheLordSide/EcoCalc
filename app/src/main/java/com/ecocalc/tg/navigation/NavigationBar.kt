package com.ecocalc.tg.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ecocalc.tg.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(selectedScreen: BottomNavItem, onItemSelected: (BottomNavItem) -> Unit) {
    NavigationBar {
        val items = listOf(BottomNavItem.MobileMoney, BottomNavItem.BankFees,BottomNavItem.History)
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedScreen == item,
                onClick = { onItemSelected(item) },
                icon = { androidx.compose.material3.Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}
