package com.dsy2204.accesapp.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    val users = mutableStateListOf<User>()
    val lastMessage = mutableStateOf<String?>(null)

    fun register(user: User): Boolean {
        if (users.size >= 5) {
            lastMessage.value = "Capacidad completa: 5 usuarios."
            return false
        }
        if (users.any { it.email.equals(user.email, true) }) {
            lastMessage.value = "El correo ya está registrado."
            return false
        }
        users.add(user)
        lastMessage.value = "Registro exitoso."
        return true
    }

    fun login(email: String, password: String): Boolean {
        val ok = users.any { it.email.equals(email, true) && it.password == password }
        lastMessage.value = if (ok) "Inicio de sesión correcto." else "Credenciales inválidas."
        return ok
    }

    fun recover(email: String): Boolean {
        val ok = users.any { it.email.equals(email, true) }
        lastMessage.value = if (ok) "Enviamos instrucciones a tu correo." else "Correo no encontrado."
        return ok
    }
}
