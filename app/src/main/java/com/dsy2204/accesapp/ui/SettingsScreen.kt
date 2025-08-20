package com.dsy2204.accesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.dsy2204.accesapp.settings.UiSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit, settings: UiSettingsViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes de accesibilidad") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics { contentDescription = "Volver" }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Modo alto contraste")
                Switch(
                    checked = settings.highContrast.value,
                    onCheckedChange = { settings.highContrast.value = it },
                    modifier = Modifier.semantics { contentDescription = "Interruptor alto contraste" }
                )
            }
            Text("Recomendado para baja visión: fondos oscuros, textos claros y mayor contraste en controles.")
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Guardar y volver" }) {
                Text("Guardar y volver")
            }
        }
    }
}
