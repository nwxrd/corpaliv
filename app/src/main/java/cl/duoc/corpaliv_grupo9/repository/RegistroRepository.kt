package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.model.EstadoRequest
import cl.duoc.corpaliv_grupo9.data.model.SolicitudRequest
import cl.duoc.corpaliv_grupo9.data.model.RegistroDto
import cl.duoc.corpaliv_grupo9.data.remote.AdminSession
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import cl.duoc.corpaliv_grupo9.data.remote.RetrofitInstance
import cl.duoc.corpaliv_grupo9.model.Registro
import retrofit2.HttpException

class RegistroRepository(private val api: ApiService = RetrofitInstance.api) {
    suspend fun guardar(request: SolicitudRequest): Registro = api.crearSolicitud(request).toDomain()

    suspend fun obtenerTodos(): List<Registro> =
        api.listarSolicitudes(AdminSession.obtenerAuthorization()).map(RegistroDto::toDomain)

    suspend fun cambiarEstado(id: Long, estado: String): Registro =
        api.cambiarEstado(AdminSession.obtenerAuthorization(), id, EstadoRequest(estado)).toDomain()

    suspend fun eliminar(id: Long) {
        val response = api.eliminarSolicitud(AdminSession.obtenerAuthorization(), id)
        if (!response.isSuccessful) throw HttpException(response)
    }
}

