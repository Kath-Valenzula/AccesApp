package com.dsy2204.accesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.connectivity.rememberIsOnline
import com.dsy2204.accesapp.navigation.AppNavHost
import com.dsy2204.accesapp.settings.UiSettingsViewModel
import com.dsy2204.accesapp.ui.components.ConnectivityBanner
import com.dsy2204.accesapp.ui.theme.AccesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()
            val auth: AuthViewModel = viewModel()
            val settings: UiSettingsViewModel = viewModel()
            val isOnline = rememberIsOnline()

            AccesAppTheme(
                highContrast = settings.highContrast.value,
                kidsMode = settings.kidsMode.value
            ) {
                Column {
                    ConnectivityBanner(isOnline.value)
                    AppNavHost(navController = nav, auth = auth, settings = settings)
                }
            }
        }
    }
}
