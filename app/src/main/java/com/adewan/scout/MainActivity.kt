package com.adewan.scout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adewan.scout.ui.features.onboarding.OnboardingNavigationHost
import com.adewan.scout.ui.theme.ScoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScoutTheme {
                OnboardingNavigationHost()
            }
        }
    }
}