package cl.duoc.corpaliv_grupo9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.corpaliv_grupo9.navigation.AppNavigation
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Corpaliv_Grupo9Theme {
                AppNavigation()
            }
        }
    }
}