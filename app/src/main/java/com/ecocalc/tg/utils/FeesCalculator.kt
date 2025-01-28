package com.ecocalc.tg.utils

object FeesCalculator {
    fun calculateMoovFees(amount: String): Double {
        val amountAsDouble = amount.toDoubleOrNull() ?: return 0.0
        return when {
            amountAsDouble in 1.0..500.0 -> 50.0
            amountAsDouble in 501.0..1000.0 -> 75.0
            amountAsDouble in 1001.0..5000.0 -> 100.0
            amountAsDouble in 5001.0..15000.0 -> 280.0
            amountAsDouble in 15001.0..20000.0 -> 320.0
            amountAsDouble in 20001.0..50000.0 -> 600.0
            amountAsDouble in 50001.0..100000.0 -> 1000.0
            amountAsDouble in 100001.0..200000.0 -> 3169.0 // ou 3356 selon le contexte
            amountAsDouble in 200001.0..300000.0 -> 3729.0 // ou 4195 selon le contexte
            amountAsDouble in 300001.0..500000.0 -> 4195.0 // ou 4381 selon le contexte
            amountAsDouble in 500001.0..850000.0 -> 4381.0 // ou 4568 selon le contexte
            amountAsDouble in 850001.0..1000000.0 -> 4568.0 // ou 5407 selon le contexte
            amountAsDouble in 1000001.0..1500000.0 -> 5407.0 // ou 8110 selon le contexte
            amountAsDouble in 1500001.0..2000000.0 -> 8110.0 // ou 9788 selon le contexte
            else -> 0.0
        }
    }


    fun calculateMixxFees(amount: String):Double{
      val amountAsDouble = amount.toDoubleOrNull()?:return 0.0

      return when{
          amountAsDouble in 1.0..1000.0 -> 8.0
          amountAsDouble in 1001.0..5000.0 -> 40.0
          amountAsDouble in 5001.0..15000.0 -> 120.0
          amountAsDouble in 15001.0..50000.0 -> 400.0
          amountAsDouble in 50001.0..100000.0 -> 800.0
          amountAsDouble in 100001.0..200000.0 -> 3600.0
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


