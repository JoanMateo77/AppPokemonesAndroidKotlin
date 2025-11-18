package com.example.pokemonappandroid.presentation.event

/**
 * Eventos de UI para las pantallas de Pokemon
 */
sealed class PokemonEvent {
    data class LoadMorePokemons(val offset: Int) : PokemonEvent()
    data class LoadPokemonDetail(val id: Int) : PokemonEvent()
    data class LoadPokemonsByType(val typeName: String) : PokemonEvent()
    object Refresh : PokemonEvent()
    object Retry : PokemonEvent()
}

