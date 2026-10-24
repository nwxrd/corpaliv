package cl.duoc.corpaliv_grupo9.model

data class Registro(
    val id:  Long,
    val nombre: String,
    val correo: String,
    val telefono:String,
    val tipoColaborador: String,
    val fecha:String,
    val estado: String,
    val mensaje: String=""
)
val tiposColaboracion = listOf(
    "Donante",
    "Alianza RSE",
    "Voluntariado",
    "Compra"
)