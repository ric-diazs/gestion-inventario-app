package com.example.gestion_inventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestion_inventario.ui.theme.Gestion_inventarioTheme
//import com.example.gestion_inventario.ui.screen.AdminHomeScreen
import com.example.gestion_inventario.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Gestion_inventarioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Vista Home de Admin
                    //AdminHomeScreen()

                    // Se implementa flujo de navegacion en vez de mostrar una vista inicial
                    AppNavigation()
                    /*
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    */
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    Gestion_inventarioTheme {
        AdminHomeScreen()
    }
}
*/
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Gestion_inventarioTheme {
        Greeting("Android")
    }
}
*/