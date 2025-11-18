package com.example.pokemonappandroid.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
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
 * Pantalla principal que muestra la lista de Pokemons
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PokemonViewModel,
    onPokemonClick: (Int) -> Unit,
    onTypesClick: () -> Unit
) {
    val listState = viewModel.pokemonListPaginatedState.collectAsState()
    val lazyGridState = rememberLazyGridState()

    // Detectar cuando se llega al final de la lista para cargar más
    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val lastVisibleItem = visibleItems.last()
                    if (lastVisibleItem.index >= listState.value.pokemons.size - 3 &&
                        !listState.value.isLoading &&
                        listState.value.hasMore
                    ) {
                        viewModel.onEvent(PokemonEvent.LoadMorePokemons(listState.value.currentOffset))
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon App") },
                actions = {
                    // Menú desplegable
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
                                text = { Text("Ver Tipos") },
                                onClick = {
                                    expanded = false
                                    onTypesClick()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            listState.value.pokemons.isEmpty() && listState.value.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            listState.value.error != null -> {
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
                            text = "Error: ${listState.value.error}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = { viewModel.onEvent(PokemonEvent.Retry) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = lazyGridState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listState.value.pokemons) { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon.id) }
                        )
                    }

                    if (listState.value.isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}
