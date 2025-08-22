package com.dsy2204.accesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.settings.UiSettingsViewModel
import com.dsy2204.accesapp.ui.LoginScreen
import com.dsy2204.accesapp.ui.RegisterScreen
import com.dsy2204.accesapp.ui.RecoverPasswordScreen
import com.dsy2204.accesapp.ui.AssistScreen
import com.dsy2204.accesapp.ui.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController, auth: AuthViewModel, settings: UiSettingsViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onRegister = { navController.navigate("register") },
                onRecover = { navController.navigate("recover") },
                onSuccess = { navController.navigate("assist") },
                auth = auth
            )
        }
        composable("register") { RegisterScreen(onBack = { navController.popBackStack() }, auth = auth) }
        composable("recover") { RecoverPasswordScreen(onBack = { navController.popBackStack() }, auth = auth) }
        composable("assist") { AssistScreen(onBack = { navController.popBackStack() }, onOpenSettings = { navController.navigate("settings") }, auth = auth) }
        composable("settings") { SettingsScreen(onBack = { navController.popBackStack() }, settings = settings) }
    }
}
