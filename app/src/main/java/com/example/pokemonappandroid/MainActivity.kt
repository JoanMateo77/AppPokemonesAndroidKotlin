package com.example.pokemonappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pokemonappandroid.core.di.AppModule
import com.example.pokemonappandroid.presentation.navigation.NavGraph
import com.example.pokemonappandroid.presentation.viewmodel.PokemonViewModel
import com.example.pokemonappandroid.presentation.ui.theme.PokemonAppAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Actividad principal de la aplicación
 * Host de Compose y Navigation
 */
class MainActivity : ComponentActivity() {
    
    private val viewModel: PokemonViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar Koin (solo una vez)
        if (org.koin.core.context.GlobalContext.getOrNull() == null) {
            AppModule.init(application)
        }
        
        enableEdgeToEdge()
        setContent {
            PokemonAppAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(viewModel = viewModel)
                }
            }
        }
    }
}
