package com.ecocalc.tg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ecocalc.tg.screens.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pour Android 12 et versions supérieures, on utilise l'API SplashScreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = splashScreen
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.remove()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            // Pour les versions inférieures à Android 12, utilise un délai comme montré plus haut
            setContent {
                SplashScreen()
            }
            android.os.Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
}
