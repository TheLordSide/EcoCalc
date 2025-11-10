package com.ecocalc.tg.utils

object FeesCalculator {
    fun calculateMoovFees(amount: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        return when (amountAsDouble) {
            in 1.0..500.0 -> 50.0
            in 501.0..1000.0 -> 75.0
            in 1001.0..5000.0 -> 100.0
            in 5001.0..15000.0 -> 280.0
            in 15001.0..20000.0 -> 320.0
            in 20001.0..50000.0 -> 600.0
            in 50001.0..100000.0 -> 1000.0
            in 100001.0..200000.0 -> 3169.0 // ou 3356 selon le contexte
            in 200001.0..300000.0 -> 3729.0 // ou 4195 selon le contexte
            in 300001.0..500000.0 -> 4195.0 // ou 4381 selon le contexte
            in 500001.0..850000.0 -> 4381.0 // ou 4568 selon le contexte
            in 850001.0..1000000.0 -> 4568.0 // ou 5407 selon le contexte
            in 1000001.0..1500000.0 -> 5407.0 // ou 8110 selon le contexte
            in 1500001.0..2000000.0 -> 8110.0 // ou 9788 selon le contexte
            else -> 0.0
        }
    }


    fun calculateMixxFees(amount: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0

        return when (amountAsDouble) {
            in 1.0..500.0 -> 50.0
            in 501.0..5000.0 -> 100.0
            in 5001.0..20000.0 -> 300.0
            in 20001.0..50000.0 -> 600.0
            in 50001.0..100000.0 -> 1000.0
            in 100001.0..200000.0 -> 3100.0
            in 200001.0..300000.0 -> 3700.0
            in 300001.0..400000.0 -> 4200.0
            in 400001.0..500000.0 -> 4400.0
            in 500001.0..600000.0 -> 4600.0
            in 600001.0..700000.0 -> 4900.0
            in 700001.0..800000.0 -> 5100.0
            in 800001.0..900000.0 -> 5400.0
            in 900001.0..1000000.0 -> 5900.0
            in 1000001.0..1050000.0 -> 6200.0
            in 1050001.0..1100000.0 -> 6500.0
            in 1100001.0..1150000.0 -> 6800.0
            in 1150001.0..1200000.0 -> 7000.0
            in 1200001.0..1250000.0 -> 7300.0
            in 1250001.0..1300000.0 -> 7600.0
            in 1300001.0..1400000.0 -> 7800.0
            in 1400001.0..1500000.0 -> 9700.0
            else -> 0.0
        }
    }



    /*Ajoute d'autres fournisseurs ici
    fun calculateMTNFees(amount: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        // Ajoute la logique pour MTN ici
        return 0.0
    }

    fun calculateOrangeFees(amount: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        // Ajoute la logique pour Orange ici
        return 0.0
    }
   */

}


