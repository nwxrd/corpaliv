package cl.duoc.corpaliv_grupo9.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class CorpalivDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {
        createUsersTable(database)
        insertInitialAdmin(database)
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

    companion object {
        private const val DATABASE_NAME = "corpaliv.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD_SALT = "password_salt"
        private const val COLUMN_PASSWORD_HASH = "password_hash"
        private const val PASSWORD_ITERATIONS = 120_000
        private const val PASSWORD_KEY_LENGTH_BITS = 256
        private const val PASSWORD_SALT_LENGTH_BYTES = 16
        private const val INITIAL_ADMIN_USERNAME = "admin"
        private const val INITIAL_ADMIN_PASSWORD = "admin1234"

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
