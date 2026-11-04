package cl.duoc.corpaliv_grupo9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import cl.duoc.corpaliv_grupo9.data.CorpalivDatabase
import cl.duoc.corpaliv_grupo9.navigation.AppNavigation
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = CorpalivDatabase.getInstance(applicationContext)
        lifecycleScope.launch(Dispatchers.IO) {
            database.openConnection()
        }

        setContent {
            Corpaliv_Grupo9Theme {
                AppNavigation()
            }
        }
    }
}