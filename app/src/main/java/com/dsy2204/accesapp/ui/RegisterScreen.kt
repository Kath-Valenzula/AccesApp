package com.dsy2204.accesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.auth.User

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("DEPRECATION")
@Composable
fun RegisterScreen(onBack: () -> Unit, auth: AuthViewModel) {
    val snackbar = remember { SnackbarHostState() }
    val focus = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var accepted by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("Usuario") }
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("Español", "English")
    var language by remember { mutableStateOf(languages.first()) }
    val limitFree = auth.users.size < AuthViewModel.MAX_USERS
    val formValid = limitFree && name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirm.isNotBlank() && accepted && password == confirm

    LaunchedEffect(auth.lastMessage.value) {
        auth.lastMessage.value?.let { snackbar.showSnackbar(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics { contentDescription = "Volver" }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Usuarios registrados: ${auth.users.size}/${AuthViewModel.MAX_USERS}")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo nombre" }
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo correo electrónico" }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo contraseña" }
            )
            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirmar contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo confirmar contraseña" }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { role = "Usuario" }) {
                    RadioButton(selected = role == "Usuario", onClick = { role = "Usuario" })
                    Text("Usuario")
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { role = "Tutor" }) {
                    RadioButton(selected = role == "Tutor", onClick = { role = "Tutor" })
                    Text("Tutor")
                }
            }
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = language,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Idioma") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .semantics { contentDescription = "Selector de idioma" }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    languages.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = { language = it; expanded = false })
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Checkbox(checked = accepted, onCheckedChange = { accepted = it })
                Text("Acepto términos y condiciones")
            }
            Button(
                enabled = formValid,
                onClick = {
                    focus.clearFocus()
                    val ok = auth.register(
                        User(
                            name = name.trim(),
                            email = email.trim(),
                            password = password,
                            role = role,
                            language = language,
                            acceptedTerms = accepted
                        )
                    )
                    if (ok) onBack()
                },
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Botón crear cuenta" }
            ) {
                Text(if (limitFree) "Crear cuenta" else "Capacidad completa")
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth().weight(1f, false)) {
                items(auth.users) { u ->
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(u.name)
                        Text(u.email)
                    }
                }
            }
        }
    }
}
