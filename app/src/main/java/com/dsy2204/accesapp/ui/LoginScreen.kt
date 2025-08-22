package com.dsy2204.accesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.dsy2204.accesapp.a11y.AccessibleMessage
import com.dsy2204.accesapp.a11y.ScreenHeading
import com.dsy2204.accesapp.a11y.a11yButton
import com.dsy2204.accesapp.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onRegister: () -> Unit, onRecover: () -> Unit, onSuccess: () -> Unit, auth: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val emailFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { emailFocus.requestFocus() }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AccessibleMessage(auth.lastMessage.value)
            ScreenHeading("Iniciar sesión")
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().focusRequester(emailFocus).semantics { contentDescription = "Campo correo electrónico" }
            )
            TextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo contraseña" }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text("Recordarme")
            }
            Button(
                onClick = { if (auth.login(email, pass)) onSuccess() },
                enabled = email.isNotBlank() && pass.isNotBlank(),
                modifier = Modifier.fillMaxWidth().a11yButton().semantics { contentDescription = "Ingresar" }
            ) { Text("Ingresar") }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onRegister, modifier = Modifier.weight(1f).a11yButton()) { Text("Registrarme") }
                TextButton(onClick = onRecover, modifier = Modifier.weight(1f).a11yButton()) { Text("Olvidé mi contraseña") }
            }

            Text("Usuarios registrados")
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth().weight(1f, false).semantics { contentDescription = "Lista de usuarios registrados" }
            ) {
                items(auth.users) { u ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Text(u.name, modifier = Modifier.weight(1f))
                        Text(u.role)
                        Text(u.email)
                    }
                }
            }
        }
    }
}
