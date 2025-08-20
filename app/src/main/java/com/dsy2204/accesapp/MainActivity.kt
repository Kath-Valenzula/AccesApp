package com.dsy2204.accesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.navigation.AppNavHost
import com.dsy2204.accesapp.settings.UiSettingsViewModel
import com.dsy2204.accesapp.ui.theme.AccesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val auth: AuthViewModel = viewModel()
            val settings: UiSettingsViewModel = viewModel()
            AccesAppTheme(highContrast = settings.highContrast.value) {
                AppNavHost(navController = navController, auth = auth, settings = settings)
            }
        }
    }
}
