package cl.duoc.corpaliv_grupo9.model

sealed class TipoColaboracion(val etiqueta: String, val tipoRegistro: String) {
    data object Donante : TipoColaboracion("Donación / Aporte económico", "Donación / Aporte")
    data object AliadoRse : TipoColaboracion("Alianza RSE para Empresas", "Alianza RSE Empresa")
    data object Voluntario : TipoColaboracion("Voluntariado", "Voluntariado")
    data object Comprador : TipoColaboracion("Compra de Productos Alba Lab", "Compra Alba Lab")

    companion object {
        fun opciones(): List<TipoColaboracion> = listOf(Donante, AliadoRse, Voluntario, Comprador)
    }
}

//odo lo que el usuario escribe en el
data class FormularioUiState(
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val confirmarCorreo: String = "",
    val telefono: String = "",
    val tipo: TipoColaboracion? = null,
    val monto: String = "",
    val mensaje: String = "",
    val enviando: Boolean = false,
    val errores: FormularioErrores = FormularioErrores()
)


data class FormularioErrores(
    val nombre: String? = null,
    val apellidos: String? = null,
    val correo: String? = null,
    val confirmarCorreo: String? = null,
    val telefono: String? = null,
    val tipo: String? = null,
    val monto: String? = null
)