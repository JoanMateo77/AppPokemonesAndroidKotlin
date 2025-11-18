package com.example.pokemonappandroid.presentation.state

import com.example.pokemonappandroid.domain.model.Pokemon
import com.example.pokemonappandroid.domain.model.PokemonDetail

/**
 * Estados de UI para la pantalla de lista de Pokemons
 */
sealed class PokemonListUiState {
    object Loading : PokemonListUiState()
    data class Success(val pokemons: List<Pokemon>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}

/**
 * Estados de UI para la pantalla de detalle de Pokemon
 */
sealed class PokemonDetailUiState {
    object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonDetail) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}

/**
 * Estados de UI para la pantalla de tipos de Pokemon
 */
sealed class PokemonTypeUiState {
    object Loading : PokemonTypeUiState()
    data class Success(val pokemons: List<Pokemon>) : PokemonTypeUiState()
    data class Error(val message: String) : PokemonTypeUiState()
}

