package com.dsy2204.accesapp.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    companion object {
        const val MAX_USERS = 5
    }

    val users = mutableStateListOf<User>()
    val lastMessage = mutableStateOf<String?>(null)
    val currentUser = mutableStateOf<User?>(null)

    val notes = mutableStateListOf<String>()

    fun register(user: User): Boolean {
        if (users.size >= MAX_USERS) {
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
        val found = users.firstOrNull { it.email.equals(email, true) && it.password == password }
        currentUser.value = found
        lastMessage.value = if (found != null) "Inicio de sesión correcto." else "Credenciales inválidas."
        return found != null
    }

    fun recover(email: String): Boolean {
        val ok = users.any { it.email.equals(email, true) }
        lastMessage.value = if (ok) "Enviamos instrucciones a tu correo." else "Correo no encontrado."
        return ok
    }

    fun saveNote(text: String) {
        if (text.isNotBlank()) {
            notes.add(0, text)
            lastMessage.value = "Nota guardada."
        }
    }

    fun deleteNote(index: Int) {
        if (index in notes.indices) notes.removeAt(index)
    }
}
