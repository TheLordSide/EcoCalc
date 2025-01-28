package com.ecocalc.tg.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    data object MobileMoney : BottomNavItem("Mobile Money", Icons.Default.MobileFriendly)
    data object BankFees : BottomNavItem("Frais Bancaires", Icons.Default.AccountBalance)
    data object History : BottomNavItem("A propos", Icons.Default.PermDeviceInformation)
}
