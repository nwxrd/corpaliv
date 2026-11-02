package cl.duoc.corpaliv_grupo9.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.content.ContentValues
import cl.duoc.corpaliv_grupo9.model.Registro

class CorpalivDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {
        createUsersTable(database)
        insertInitialAdmin(database)
        createRegistrosTable(database)
    }

    override fun onUpgrade(
        database: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < 2) {
            createUsersTable(database)
            insertInitialAdmin(database)
        }
        if(oldVersion<3){
            createRegistrosTable(database)
        }
    }

    fun openConnection(): SQLiteDatabase = writableDatabase

    fun verifyCredentials(username: String, password: String): Boolean {
        if (username.isBlank() || password.isEmpty()) return false

        readableDatabase.query(
            TABLE_USERS,
            arrayOf(COLUMN_PASSWORD_SALT, COLUMN_PASSWORD_HASH),
            "$COLUMN_USERNAME = ?",
            arrayOf(username.trim()),
            null,
            null,
            null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return false

            val salt = cursor.getBlob(0)
            val expectedHash = cursor.getBlob(1)
            val actualHash = hashPassword(password, salt)
            return java.security.MessageDigest.isEqual(expectedHash, actualHash)
        }
    }
    fun insertarRegistro(registro: Registro):Long {
        val valores = ContentValues().apply{
            put(COLUMN_NOMBRE, registro.nombre)
            put(COLUMN_CORREO, registro.correo)
            put(COLUMN_TELEFONO, registro.telefono)
            put(COLUMN_TIPO, registro.tipoColaborador)
            put(COLUMN_FECHA, registro.fecha)
            put(COLUMN_ESTADO, registro.estado)
            put(COLUMN_MENSAJE, registro.mensaje)

        }
        return writableDatabase.insert(TABLE_REGISTROS, null, valores)
    }
    fun obtenerRegistros(): List<Registro> {
        val lista = mutableListOf<Registro>()
        readableDatabase.query(
            TABLE_REGISTROS, null, null, null, null, null, "id DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                lista.add(
                    Registro(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO)),
                        telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO)),
                        tipoColaborador = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)),
                        fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                        estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO)),
                        mensaje = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENSAJE)) ?: ""
                    )
                )
            }
        }
        return lista
    }


    fun actualizarEstado(id: Long, estado: String): Int {
        val valores = ContentValues().apply { put(COLUMN_ESTADO, estado) }
        return writableDatabase.update(
            TABLE_REGISTROS, valores, "id = ?", arrayOf(id.toString())
        )
    }

    companion object {
        private const val DATABASE_NAME = "corpaliv.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_USERS = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD_SALT = "password_salt"
        private const val COLUMN_PASSWORD_HASH = "password_hash"
        private const val PASSWORD_ITERATIONS = 120_000
        private const val PASSWORD_KEY_LENGTH_BITS = 256
        private const val PASSWORD_SALT_LENGTH_BYTES = 16
        private const val INITIAL_ADMIN_USERNAME = "admin"
        private const val INITIAL_ADMIN_PASSWORD = "admin1234"


        // Tabla de registros
        private const val TABLE_REGISTROS = "registros"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_TELEFONO = "telefono"
        private const val COLUMN_TIPO = "tipo_colaborador"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_ESTADO = "estado"
        private const val COLUMN_MENSAJE = "mensaje"

        @Volatile
        private var instance: CorpalivDatabase? = null

        private fun createUsersTable(database: SQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS $TABLE_USERS (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USERNAME TEXT NOT NULL UNIQUE COLLATE NOCASE,
                    $COLUMN_PASSWORD_SALT BLOB NOT NULL,
                    $COLUMN_PASSWORD_HASH BLOB NOT NULL
                )
                """.trimIndent()
            )
        }
        private fun createRegistrosTable(database: SQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS registros (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    correo TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    tipo_colaborador TEXT NOT NULL,
                    fecha TEXT NOT NULL,
                    estado TEXT NOT NULL,
                    mensaje TEXT
                )
                """.trimIndent()
            )
        }

        private fun insertInitialAdmin(database: SQLiteDatabase) {
            val salt = ByteArray(PASSWORD_SALT_LENGTH_BYTES).also(SecureRandom()::nextBytes)
            val passwordHash = hashPassword(INITIAL_ADMIN_PASSWORD, salt)
            database.execSQL(
                "INSERT OR IGNORE INTO $TABLE_USERS " +
                    "($COLUMN_USERNAME, $COLUMN_PASSWORD_SALT, $COLUMN_PASSWORD_HASH) VALUES (?, ?, ?)",
                arrayOf(INITIAL_ADMIN_USERNAME, salt, passwordHash)
            )
        }

        private fun hashPassword(password: String, salt: ByteArray): ByteArray {
            val spec = PBEKeySpec(
                password.toCharArray(),
                salt,
                PASSWORD_ITERATIONS,
                PASSWORD_KEY_LENGTH_BITS
            )
            return try {
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(spec)
                    .encoded
            } finally {
                spec.clearPassword()
            }
        }

        fun getInstance(context: Context): CorpalivDatabase {
            return instance ?: synchronized(this) {
                instance ?: CorpalivDatabase(context).also { instance = it }
            }
        }
    }
}
