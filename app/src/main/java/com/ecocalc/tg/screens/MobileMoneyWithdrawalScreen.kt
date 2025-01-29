@file:OptIn(ExperimentalMaterial3Api::class)

package com.ecocalc.tg.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ecocalc.tg.utils.FeesCalculator // Import de la classe de calcul des frais

@Composable
fun MobileMoneyWithdrawalScreen() {
    var withdrawalAmount by remember { mutableStateOf("") }
    var selectedProvider by remember { mutableStateOf("Moov Money") }
    var calculatedFees by remember { mutableStateOf(0.0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val providers = listOf("Moov Money", "Mixx by Yas")

    val maxAmount = when (selectedProvider) {
        "Moov Money" -> 2000000.0
        "Mixx by Yas" -> 200000.0
        else -> 100000.0
    }

    // Calcul des frais basé sur le fournisseur sélectionné
    fun calculateFees(amount: String, provider: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: 0.0
        if (amountAsDouble > maxAmount) {
            errorMessage = "Le montant maximum autorisé pour $selectedProvider est ${maxAmount.toInt()} F CFA."
            return 0.0
        } else {
            errorMessage = ""
        }
        return when (provider) {
            "Moov Money" -> FeesCalculator.calculateMoovFees(amount)
            "Mixx by Yas" -> FeesCalculator.calculateMixxFees(amount)
            else -> 0.0
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = withdrawalAmount,
            onValueChange = { withdrawalAmount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Montant du retrait") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            isError = errorMessage.isNotEmpty(),
            trailingIcon = {
                if (withdrawalAmount.isNotEmpty()) {
                    IconButton(onClick = { withdrawalAmount = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Effacer le champs"
                        )
                    }
                }
            }
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it },
        ) {
            OutlinedTextField(
                value = selectedProvider,
                onValueChange = {},
                label = { Text("Fournisseur") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand Dropdown"
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                providers.forEach { provider ->
                    DropdownMenuItem(
                        text = { Text(provider) },
                        onClick = {
                            selectedProvider = provider
                            isDropdownExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            calculatedFees = calculateFees(withdrawalAmount, selectedProvider)
            if (errorMessage.isEmpty()) {
                isDialogVisible = true
            }
        }) {
            Text("Calculer les frais")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                title = { Text("Résultat") },
                text = {
                    if (calculatedFees > 0.0) {
                        Text("Frais pour $selectedProvider : ${"%.2f".format(calculatedFees)}")
                    } else {
                        Text("Veuillez entrer un montant valide.")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { isDialogVisible = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
