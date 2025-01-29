package com.ecocalc.tg.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.datastore by preferencesDataStore(name = "user_preferences")

object PreferencesKeys {
    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
}

// Fonction pour sauvegarder l'état de l'onboarding
suspend fun saveOnboardingCompleted(context: Context) {
    context.datastore.edit { preferences ->
        preferences[PreferencesKeys.ONBOARDING_COMPLETED] = true
    }
}

// Fonction pour vérifier si l'onboarding est terminé
suspend fun isOnboardingCompleted(context: Context): Boolean {
    val preferences = context.datastore.data.first()  // Récupère les préférences
    return preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false  // Retourne la valeur ou false si non définie
}
