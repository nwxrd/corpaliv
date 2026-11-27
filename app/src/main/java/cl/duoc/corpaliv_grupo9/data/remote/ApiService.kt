package cl.duoc.corpaliv_grupo9.data.remote

import cl.duoc.corpaliv_grupo9.data.model.EstadoRequest
import cl.duoc.corpaliv_grupo9.data.model.ProductoDto
import cl.duoc.corpaliv_grupo9.data.model.SolicitudRequest
import cl.duoc.corpaliv_grupo9.data.model.RegistroDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/v1/productos")
    suspend fun listarProductos(): List<ProductoDto>

    @POST("api/v1/solicitudes")
    suspend fun crearSolicitud(@Body request: SolicitudRequest): RegistroDto

    @GET("api/v1/admin/session")
    suspend fun validarSesion(@Header("Authorization") authorization: String): Response<Unit>

    @GET("api/v1/admin/solicitudes")
    suspend fun listarSolicitudes(@Header("Authorization") authorization: String): List<RegistroDto>

    @GET("api/v1/admin/solicitudes/{id}")
    suspend fun obtenerSolicitud(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long
    ): RegistroDto

    @PUT("api/v1/admin/solicitudes/{id}")
    suspend fun actualizarSolicitud(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Body request: SolicitudRequest
    ): RegistroDto

    @PATCH("api/v1/admin/solicitudes/{id}/estado")
    suspend fun cambiarEstado(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Body request: EstadoRequest
    ): RegistroDto

    @DELETE("api/v1/admin/solicitudes/{id}")
    suspend fun eliminarSolicitud(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long
    ): Response<Unit>
}