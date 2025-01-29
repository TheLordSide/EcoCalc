package com.ecocalc.tg.onboarding

import com.ecocalc.tg.R

sealed class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
) {
    object Page1 : OnboardingPage(
        title = "Bienvenue",
        description = "Decouvrez EcoCalc",
        image = R.drawable.logo_light

    )

    object Page2 : OnboardingPage(
        title = "Que proposons-nous?",
        description = "Nous proposons des calculs des frais de transactions",
        image = R.drawable.logo_light

    )
    companion object {
        val OnboardingPages = listOf(Page1, Page2)
    }

}



