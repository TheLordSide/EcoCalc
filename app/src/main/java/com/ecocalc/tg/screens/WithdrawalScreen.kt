@file:OptIn(ExperimentalMaterial3Api::class)

package com.ecocalc.tg.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun WithdrawalScreen() {
    var withdrawalAmount by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("Ecobank") }
    var calculatedFees by remember { mutableDoubleStateOf(0.0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }

    val banks = listOf("Ecobank", "Orange Bank", "MTN Bank")

    // Fonction pour calculer les frais
    fun calculateFees(amount: String, bank: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        return when (bank) {
            "Ecobank" -> amountAsDouble * 0.015
            "Orange Bank" -> amountAsDouble * 0.025
            "MTN Bank" -> amountAsDouble * 0.01
            else -> 0.0
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Champ de saisie pour la somme à retirer
        OutlinedTextField(
            value = withdrawalAmount,
            onValueChange = { withdrawalAmount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Montant du retrait") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it },
        ) {
            TextField(
                value = selectedBank,
                onValueChange = {},
                label = { Text("Banque") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                banks.forEach { bank ->
                    DropdownMenuItem(
                        text = { Text(bank) },
                        onClick = {
                            selectedBank = bank
                            isDropdownExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bouton pour effectuer le calcul
        Button(onClick = {
            calculatedFees = calculateFees(withdrawalAmount, selectedBank)
            isDialogVisible = true // Affiche le popup
        }) {
            Text("Calculer les frais")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Popup (dialog) pour afficher les résultats
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                title = { Text("Résultat") },
                text = {
                    if (calculatedFees > 0.0) {
                        Text("Frais pour $selectedBank : ${"%.2f".format(calculatedFees)}")
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