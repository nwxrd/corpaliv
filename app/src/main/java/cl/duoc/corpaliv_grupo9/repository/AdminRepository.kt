package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.remote.AdminSession
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import cl.duoc.corpaliv_grupo9.data.remote.RetrofitInstance

class AdminRepository(private val api: ApiService = RetrofitInstance.api) {
    suspend fun iniciarSesion(username: String, password: String): Boolean {
        val authorization = AdminSession.crearAuthorization(username, password)
        val response = api.validarSesion(authorization)
        if (!response.isSuccessful) return false
        AdminSession.iniciar(authorization)
        return true
    }
}