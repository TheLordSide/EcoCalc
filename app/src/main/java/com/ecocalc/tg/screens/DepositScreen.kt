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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositScreen() {
    var depositAmount by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("ECOBANK - Carte CashXpress Visa") }
    var calculatedDepositFee by remember { mutableDoubleStateOf(0.0) }
    var calculatedPaymentFee by remember { mutableDoubleStateOf(0.0) }
    var totalCalculatedFees by remember { mutableDoubleStateOf(0.0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val banks = listOf(
//        "CORIS BANK-CORIS CASH",
//        "ORABANK-Carte VISA Liberté",
        "ECOBANK - Carte CashXpress Visa")
    var errorMessage by remember { mutableStateOf("") }

    // --- Constantes pour les calculs de frais ---
    val ecobankFeeRate = 0.015 // Frais bancaires de 1.5% pour ECOBANK
    val tafFees = 0.1 // TAF de 10%
    val paymentTtcFeeRate = 0.025 // Taxe de 2.5% pour les frais de paiement
    val minDepositBankFee = 1000.0 // Frais bancaires de dépôt minimum
    val minTafOnPayment = 600.0 // TAF minimum sur les frais de paiement

    /**
     * Calcule les frais de DÉPÔT (partie 1 du calcul).
     */
    fun calculateDepositFees(amount: String, bank: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0

        // 1. Frais bancaires de base
        var bankDepositFees = when (bank) {
            "ECOBANK - Carte CashXpress Visa" -> amountAsDouble * ecobankFeeRate
            else -> 0.0
        }

        // 2. Appliquer les frais bancaires minimum
        if (bankDepositFees < minDepositBankFee) {
            bankDepositFees = minDepositBankFee
        }

        // 3. Ajouter la TAF sur les frais de dépôt
        val tafOnDeposit = bankDepositFees * tafFees
        val totalDepositFees = bankDepositFees + tafOnDeposit

        return totalDepositFees
    }

    /**
     * Calcule les frais de PAIEMENT TTC (partie 2 du calcul).
     * Ces frais sont calculés sur le MONTANT INITIAL déposé.
     */
    fun calculateTTCPayFees(amount: String, bank: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0

        // 1. Calculer la taxe de 2.5% sur le montant initial
        var ttcFees = amountAsDouble * paymentTtcFeeRate
        if (ttcFees < minTafOnPayment) {
            ttcFees = minTafOnPayment
        }

        // 2. Calculer la TAF sur cette taxe, avec un minimum
        val tafOnPayment = ttcFees * tafFees


        // 3. Les frais de paiement TTC sont la somme de ces deux taxes
        return ttcFees + tafOnPayment
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
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable,enabled = true),
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
            if (depositAmount.toDoubleOrNull() != null) {
                // Calcule et stocke chaque partie des frais
                calculatedDepositFee = calculateDepositFees(depositAmount, selectedBank)
                calculatedPaymentFee = calculateTTCPayFees(depositAmount, selectedBank)
                totalCalculatedFees = calculatedDepositFee + calculatedPaymentFee
            } else {
                calculatedDepositFee = 0.0
                calculatedPaymentFee = 0.0
                totalCalculatedFees = 0.0
            }

            isDialogVisible = true // Affiche le popup
        }) {
            Text("Calculer les frais")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Popup (dialog) pour afficher les résultats
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                title = { Text("Détails des frais") },
                text = {
                    if (totalCalculatedFees > 0.0) {
                        Column {
                            Text("Pour la carte $selectedBank:")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Frais de dépôt : ${ "%.2f".format(calculatedDepositFee)} FCFA")
                            Text("Frais de paiement : ${ "%.2f".format(calculatedPaymentFee)} FCFA")
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                modifier = Modifier,
                                thickness = DividerDefaults.Thickness,
                                color = DividerDefaults.color
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Frais totaux estimés : ${ "%.2f".format(totalCalculatedFees)} FCFA",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Montant total estimé à prévoir : ${ "%.2f".format(totalCalculatedFees + depositAmount.toDouble())} FCFA",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
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