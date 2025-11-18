package com.example.pokemonappandroid.domain.model

/**
 * Modelo de dominio para los detalles completos de un Pokemon
 * Contiene toda la información detallada de un Pokemon
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val imageUrlShiny: String?,
    val imageUrlBack: String?,
    val baseExperience: Int?,
    val height: Int, // en decímetros
    val weight: Int, // en hectogramos
    val order: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val abilities: List<PokemonAbility>
)

data class PokemonType(
    val name: String,
    val slot: Int
)

data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int
)

data class PokemonAbility(
    val name: String,
    val isHidden: Boolean,
    val slot: Int
)
