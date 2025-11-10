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
import com.ecocalc.tg.utils.FeesCalculator // Import de la classe de calcul des frais

@Composable
fun MobileMoneyDepositScreen() {
    var depositAmount by remember { mutableStateOf("") }
    var selectedProvider by remember { mutableStateOf("Moov Money") }
    var calculatedFees by remember { mutableDoubleStateOf(0.0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val providers = listOf("Moov Money", "Mixx by Yas")

    val maxAmount = when (selectedProvider) {
        "Moov Money" -> 2000000.0
        "Mixx by Yas" -> 200000.0
        else -> 100000.0
    }

    // TODO: Adapter la logique de calcul pour les frais de DÉPÔT
    fun calculateFees(amount: String, provider: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: 0.0
        if (amountAsDouble > maxAmount) {
            errorMessage = "Le montant maximum autorisé pour $selectedProvider est ${maxAmount.toInt()} F CFA."
            return 0.0
        } else {
            errorMessage = ""
        }
        // NOTE: Utilise pour l'instant la même logique que le retrait. À adapter.
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
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
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
            calculatedFees = calculateFees(depositAmount, selectedProvider)
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
                title = { Text("Détails du dépôt") },
                text = {
                     if (calculatedFees > 0.0) {
                        val amountAsDouble = depositAmount.toDoubleOrNull() ?: 0.0
                        Column {
                            Text("Pour un dépôt via $selectedProvider:")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Montant du dépôt : ${ "%.2f".format(amountAsDouble)} FCFA")
                            Text("Frais estimés : ${ "%.2f".format(calculatedFees)} FCFA")
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                modifier = Modifier,
                                thickness = DividerDefaults.Thickness,
                                color = DividerDefaults.color
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Total à prévoir : ${ "%.2f".format(amountAsDouble + calculatedFees)} FCFA",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Montant qui sera crédité : ${ "%.2f".format(amountAsDouble)} FCFA",
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
