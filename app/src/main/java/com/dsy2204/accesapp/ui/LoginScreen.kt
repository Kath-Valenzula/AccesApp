package com.dsy2204.accesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.dsy2204.accesapp.auth.AuthViewModel
import com.dsy2204.accesapp.auth.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onRegister: () -> Unit, onRecover: () -> Unit, auth: AuthViewModel) {
    val snackbar = remember { SnackbarHostState() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("Usuario") }
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("Español", "English")
    var language by remember { mutableStateOf(languages.first()) }

    LaunchedEffect(auth.lastMessage.value) {
        auth.lastMessage.value?.let { snackbar.showSnackbar(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Iniciar sesión") }) },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Campo correo electrónico" }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Campo contraseña" }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text("Recordarme", modifier = Modifier.semantics { contentDescription = "Recordarme" })
            }
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
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    languages.forEach {
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                language = it
                                expanded = false
                            }
                        )
                    }
                }
            }
            Button(
                onClick = { auth.login(email.trim(), password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Botón ingresar" }
            ) {
                Icon(Icons.Default.Login, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Ingresar")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClickableText(
                    text = AnnotatedString("Registrarme"),
                    onClick = { onRegister() },
                    modifier = Modifier.semantics { contentDescription = "Ir a registrarme" }
                )
                ClickableText(
                    text = AnnotatedString("Olvidé mi contraseña"),
                    onClick = { onRecover() },
                    modifier = Modifier.semantics { contentDescription = "Ir a recuperar contraseña" }
                )
            }
            Divider()
            Text("Usuarios registrados")
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth().weight(1f, false)
            ) {
                items(auth.users) { u: User ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(u.name)
                        Text(u.email)
                    }
                }
            }
        }
    }
}
