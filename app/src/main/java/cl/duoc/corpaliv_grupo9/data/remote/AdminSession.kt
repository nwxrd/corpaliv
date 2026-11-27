package cl.duoc.corpaliv_grupo9.data.remote

import java.nio.charset.StandardCharsets
import android.util.Base64

object AdminSession {
    @Volatile
    private var authorization: String? = null

    fun crearAuthorization(username: String, password: String): String {
        val encoded = Base64.encodeToString(
            "$username:$password".toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP
        )
        return "Basic $encoded"
    }

    fun iniciar(value: String) {
        authorization = value
    }

    fun obtenerAuthorization(): String =
        checkNotNull(authorization) { "La sesión de administrador no está iniciada" }

    fun cerrar() {
        authorization = null
    }
}