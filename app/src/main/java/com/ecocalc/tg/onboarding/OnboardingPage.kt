package com.ecocalc.tg.onboarding

import com.ecocalc.tg.R

sealed class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
) {
    data object Page1 : OnboardingPage(
        title = "Bienvenue!!!",
        description = "Decouvrez EcoCalc",
        image = R.drawable.logo_light

    )

    data object Page2 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Calculez les frais de tranactions",
        image = R.drawable.logo_light

    )
    data object Page3 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Des services de paiement mobile",
        image = R.drawable.mobilemoney

    )
    data object Page4 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Des cartes de crédit Prepayées",
        image = R.drawable.prepaid

    )
    companion object {
        val OnboardingPages = listOf(Page1, Page2,Page3,Page4)
    }

}



