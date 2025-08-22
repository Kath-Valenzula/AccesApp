package com.dsy2204.accesapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConnectivityBanner(isOnline: Boolean) {
    if (!isOnline) {
        Surface(color = MaterialTheme.colorScheme.errorContainer) {
            Text(
                text = "Sin conexión a internet",
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            )
        }
    }
}

