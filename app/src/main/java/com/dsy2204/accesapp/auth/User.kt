package com.dsy2204.accesapp.auth

data class User(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "Usuario",
    val language: String = "Español"
)
