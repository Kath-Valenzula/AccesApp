package com.dsy2204.accesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val auth: AuthViewModel = viewModel()
            MaterialTheme {
                AppNavHost(navController = navController, auth = auth)
            }
        }
    }
}
