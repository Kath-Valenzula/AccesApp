package com.dsy2204.accesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dsy2204.accesapp.a11y.ScreenHeading
import com.dsy2204.accesapp.a11y.AccessibleMessage
import com.dsy2204.accesapp.a11y.a11yButton
import com.dsy2204.accesapp.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(onBack: () -> Unit, auth: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    val emailFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { emailFocus.requestFocus() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar contraseña") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AccessibleMessage(auth.lastMessage.value)
            ScreenHeading("Ingresa tu correo")
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().focusRequester(emailFocus).semantics { contentDescription = "Campo correo electrónico" }
            )
            Button(
                onClick = { auth.recover(email) },
                enabled = email.isNotBlank(),
                modifier = Modifier.fillMaxWidth().a11yButton().semantics { contentDescription = "Enviar instrucciones" }
            ) { Text("Enviar") }
        }
    }
}
