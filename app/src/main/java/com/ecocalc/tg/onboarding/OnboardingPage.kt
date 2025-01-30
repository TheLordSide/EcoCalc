package com.ecocalc.tg.onboarding

import com.ecocalc.tg.R

sealed class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
) {
    data object Page1 : OnboardingPage(
        title = "Bienvenue",
        description = "Decouvrez EcoCalc",
        image = R.drawable.logo_light

    )

    data object Page2 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Calculer les frais lier aux transactions",
        image = R.drawable.logo_light

    )
    data object Page3 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Mobile Money",
        image = R.drawable.mobilemoney

    )
    data object Page4 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Cartes Prepayées",
        image = R.drawable.prepaid

    )
    companion object {
        val OnboardingPages = listOf(Page1, Page2,Page3,Page4)
    }

}



