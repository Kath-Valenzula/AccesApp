package com.dsy2204.accesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.dsy2204.accesapp.auth.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onBack: () -> Unit, auth: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Usuario") }
    var lang by remember { mutableStateOf("Español") }
    var terms by remember { mutableStateOf(false) }
    var langExpanded by remember { mutableStateOf(false) }
    val nameFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { nameFocus.requestFocus() }

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
            ScreenHeading("Crear cuenta")
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth().focusRequester(nameFocus).semantics { contentDescription = "Campo nombre" }
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo correo electrónico" }
            )
            TextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Campo contraseña" }
            )
            TextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Confirmar contraseña" }
            )

            Text("Rol")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row {
                    RadioButton(selected = role == "Usuario", onClick = { role = "Usuario" })
                    Text("Usuario")
                }
                Row {
                    RadioButton(selected = role == "Tutor", onClick = { role = "Tutor" })
                    Text("Tutor")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Idioma")
                Box(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable { langExpanded = true }.semantics { contentDescription = "Seleccionar idioma" }
                    ) {
                        Text(lang)
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = langExpanded, onDismissRequest = { langExpanded = false }) {
                        listOf("Español", "Inglés", "Portugués").forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = { lang = it; langExpanded = false })
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Checkbox(checked = terms, onCheckedChange = { terms = it })
                Text("Acepto términos y condiciones")
            }
            Button(
                onClick = {
                    if (pass == confirm && terms) {
                        if (auth.register(User(name = name, email = email, password = pass, role = role, language = lang))) onBack()
                    } else {
                        if (pass != confirm) auth.lastMessage.value = "Las contraseñas no coinciden" else auth.lastMessage.value = "Debes aceptar los términos"
                    }
                },
                enabled = name.isNotBlank() && email.isNotBlank() && pass.isNotBlank() && confirm.isNotBlank(),
                modifier = Modifier.fillMaxWidth().a11yButton().semantics { contentDescription = "Guardar registro" }
            ) { Text("Guardar") }
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth().a11yButton()) { Text("Volver a Iniciar sesión") }
        }
    }
}
