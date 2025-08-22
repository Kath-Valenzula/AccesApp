package com.dsy2204.accesapp.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    val users = mutableStateListOf<User>()
    val notes = mutableStateListOf<String>()
    val lastMessage = mutableStateOf<String?>(null)

    fun register(user: User): Boolean {
        if (users.size >= 5) { lastMessage.value = "Capacidad máxima de 5 usuarios alcanzada"; return false }
        if (users.any { it.email.equals(user.email, true) }) { lastMessage.value = "El correo ya está registrado"; return false }
        users.add(user)
        lastMessage.value = "Usuario registrado"
        return true
    }

    fun login(email: String, password: String): Boolean {
        val ok = users.any { it.email.equals(email, true) && it.password == password }
        lastMessage.value = if (ok) "Inicio de sesión exitoso" else "Credenciales incorrectas"
        return ok
    }

    fun recover(email: String) {
        val exists = users.any { it.email.equals(email, true) }
        lastMessage.value = if (exists) "Se envió un mensaje de recuperación a $email" else "No existe una cuenta con ese correo"
    }

    fun saveNote(text: String) {
        notes.add(text)
        lastMessage.value = "Nota guardada"
    }

    fun deleteNote(index: Int) {
        if (index in notes.indices) {
            notes.removeAt(index)
            lastMessage.value = "Nota eliminada"
        }
    }
}
