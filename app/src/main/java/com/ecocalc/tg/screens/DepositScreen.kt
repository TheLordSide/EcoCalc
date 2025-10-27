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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositScreen() {
    var depositAmount by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("CORIS BANK-CORIS CASH") }
    var calculatedFees by remember { mutableDoubleStateOf(0.0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val banks = listOf("CORIS BANK-CORIS CASH","ORABANK-Carte VISA Liberté","ECOBANK-Carte CashXpress Visa")
    var errorMessage by remember { mutableStateOf("") }
    var taf: Double
    var totalFees: Double
    // Fonction pour calculer les frais
    fun calculateFees(amount: String, bank: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        var frais = when (bank) {
            "CORIS BANK-CORIS CASH" -> amountAsDouble * 0.015
            "ORABANK-Carte VISA Liberté" -> amountAsDouble * 0.015
            "ECOBANK-Carte CashXpress Visa" -> amountAsDouble * 0.015
            else -> 0.0
        }

        if (frais < 1000.0) {
            frais = 1000.0
        }

        taf = frais * 0.1
        totalFees = frais+taf

        return totalFees

    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Champ de saisie pour la somme à déposer
        OutlinedTextField(
            value = depositAmount,
            onValueChange = { depositAmount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Montant du dépôt") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            isError = errorMessage.isNotEmpty(),
            trailingIcon = {
                if (depositAmount.isNotEmpty()) {
                    IconButton(onClick = { depositAmount = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Effacer le champs"
                        )
                    }
                }
            }
        )

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it },
        ) {
            OutlinedTextField(
                value = selectedBank,
                onValueChange = {},
                label = { Text("Banque") },
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
            calculatedFees = calculateFees(depositAmount, selectedBank)
            isDialogVisible = true // Affiche le popup
        }) {
            Text("Calculer les frais")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Popup (dialog) pour afficher les résultats
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                title = { Text("Informations") },
                text = {
                    if (calculatedFees > 0.0) {
                        Text("\n\n\nLes frais de dépôt pour la carte prépayée $selectedBank, sont de : ${"%.2f".format(calculatedFees)}")
                        Text("Le montant final à deposer sera de :${"%.2f".format(calculatedFees+depositAmount.toDouble())}")
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
