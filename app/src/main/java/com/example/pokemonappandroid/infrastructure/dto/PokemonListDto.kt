package com.example.pokemonappandroid.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de la lista de Pokemons
 */
data class PokemonListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItemDto>
)

data class PokemonListItemDto(
    val name: String,
    val url: String
)
