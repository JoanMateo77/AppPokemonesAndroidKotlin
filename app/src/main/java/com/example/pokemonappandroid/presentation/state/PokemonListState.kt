package com.example.pokemonappandroid.presentation.state

/**
 * Estado para la lista de Pokemons con paginación
 */
data class PokemonListState(
    val pokemons: List<com.example.pokemonappandroid.domain.model.Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0
)

