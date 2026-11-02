package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.CorpalivDatabase
import cl.duoc.corpaliv_grupo9.model.Registro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RegistroRepository(private val database: CorpalivDatabase) {


    suspend fun guardar(registro: Registro) = withContext(Dispatchers.IO) {
        database.insertarRegistro(registro)
    }

    suspend fun obtenerTodos(): List<Registro> = withContext(Dispatchers.IO) {
        database.obtenerRegistros()
    }

    suspend fun cambiarEstado(id: Long, estado: String) = withContext(Dispatchers.IO) {
        database.actualizarEstado(id, estado)
    }
}

