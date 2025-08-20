package com.dsy2204.accesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.settings.UiSettingsViewModel
import com.dsy2204.accesapp.ui.AssistScreen
import com.dsy2204.accesapp.ui.LoginScreen
import com.dsy2204.accesapp.ui.RecoverPasswordScreen
import com.dsy2204.accesapp.ui.RegisterScreen
import com.dsy2204.accesapp.ui.SettingsScreen

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Recover = "recover"
    const val Assist = "assist"
    const val Settings = "settings"
}

@Composable
fun AppNavHost(navController: NavHostController, auth: AuthViewModel, settings: UiSettingsViewModel) {
    NavHost(navController = navController, startDestination = Routes.Login) {
        composable(Routes.Login) {
            LoginScreen(
                onRegister = { navController.navigate(Routes.Register) },
                onRecover = { navController.navigate(Routes.Recover) },
                onSuccess = { navController.navigate(Routes.Assist) },
                auth = auth
            )
        }
        composable(Routes.Register) {
            RegisterScreen(onBack = { navController.popBackStack() }, auth = auth)
        }
        composable(Routes.Recover) {
            RecoverPasswordScreen(onBack = { navController.popBackStack() }, auth = auth)
        }
        composable(Routes.Assist) {
            AssistScreen(
                onBack = { navController.popBackStack() },
                onOpenSettings = { navController.navigate(Routes.Settings) },
                auth = auth
            )
        }
        composable(Routes.Settings) {
            SettingsScreen(onBack = { navController.popBackStack() }, settings = settings)
        }
    }
}
