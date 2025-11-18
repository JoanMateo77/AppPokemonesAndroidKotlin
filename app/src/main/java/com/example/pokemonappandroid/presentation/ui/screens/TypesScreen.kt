package com.example.pokemonappandroid.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemonappandroid.presentation.event.PokemonEvent
import com.example.pokemonappandroid.presentation.ui.components.PokemonCard
import com.example.pokemonappandroid.presentation.viewmodel.PokemonViewModel

/**
 * Pantalla que muestra Pokemons filtrados por tipo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypesScreen(
    typeName: String,
    viewModel: PokemonViewModel,
    onPokemonClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val typeState = viewModel.pokemonTypeState.collectAsState()
    
    LaunchedEffect(typeName) {
        viewModel.onEvent(PokemonEvent.LoadPokemonsByType(typeName))
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemons tipo: ${typeName.replaceFirstChar { it.uppercaseChar() }}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Menu"
                            )
                        }
                        
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Actualizar") },
                                onClick = {
                                    expanded = false
                                    viewModel.onEvent(PokemonEvent.LoadPokemonsByType(typeName))
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = typeState.value) {
            is com.example.pokemonappandroid.presentation.state.PokemonTypeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is com.example.pokemonappandroid.presentation.state.PokemonTypeUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = { viewModel.onEvent(PokemonEvent.Retry) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            
            is com.example.pokemonappandroid.presentation.state.PokemonTypeUiState.Success -> {
                if (state.pokemons.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se encontraron Pokemons de este tipo")
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.pokemons) { pokemon ->
                            PokemonCard(
                                pokemon = pokemon,
                                onClick = { onPokemonClick(pokemon.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
