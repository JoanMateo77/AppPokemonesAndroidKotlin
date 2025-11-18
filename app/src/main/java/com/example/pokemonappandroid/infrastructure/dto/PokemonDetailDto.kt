package com.example.pokemonappandroid.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para los detalles completos de un Pokemon
 */
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    val height: Int,
    val weight: Int,
    val order: Int,
    val sprites: SpritesDto,
    val types: List<TypeSlotDto>,
    val stats: List<StatDto>,
    val abilities: List<AbilitySlotDto>
)

data class SpritesDto(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_shiny")
    val backShiny: String?
)

data class TypeSlotDto(
    val slot: Int,
    val type: TypeDto
)

data class TypeDto(
    val name: String
)

data class StatDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: StatNameDto
)

data class StatNameDto(
    val name: String
)

data class AbilitySlotDto(
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
    val ability: AbilityDto
)

data class AbilityDto(
    val name: String
)
