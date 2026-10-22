package cl.duoc.corpaliv_grupo9.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set
    var emailError by mutableStateOf<String?>(null)
        private set

    fun onEmailChanged(nuevoTexto: String) {
        email = nuevoTexto

        if (nuevoTexto.isEmpty()) {
            emailError = "El campo no puede estar vacío"
        } else if (!nuevoTexto.contains("@")) {
            emailError = "El correo debe tener un @"
        } else {
            emailError = null
        }
    }
}