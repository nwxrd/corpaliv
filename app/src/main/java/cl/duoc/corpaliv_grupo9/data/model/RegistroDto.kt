package cl.duoc.corpaliv_grupo9.data.model

import cl.duoc.corpaliv_grupo9.model.Registro

data class RegistroDto(
    val id: Long,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val tipoColaborador: String,
    val fecha: String,
    val estado: String,
    val mensaje: String? = null
) {
    fun toDomain() = Registro(
        id = id,
        nombre = nombre,
        correo = correo,
        telefono = telefono,
        tipoColaborador = tipoColaborador,
        fecha = fecha,
        estado = estado,
        mensaje = mensaje.orEmpty()
    )
}