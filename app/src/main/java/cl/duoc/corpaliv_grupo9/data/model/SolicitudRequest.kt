package cl.duoc.corpaliv_grupo9.data.model

data class SolicitudRequest(
    val nombre: String,
    val correo: String,
    val telefono: String,
    val tipoColaborador: String,
    val mensaje: String
)

data class EstadoRequest(val estado: String)