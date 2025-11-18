package com.example.pokemonappandroid.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para las relaciones de daño entre tipos
 */
data class TypeDamageRelationsDto(
    @SerializedName("no_damage_to")
    val noDamageTo: List<NamedResourceDto>?,
    @SerializedName("half_damage_to")
    val halfDamageTo: List<NamedResourceDto>?,
    @SerializedName("double_damage_to")
    val doubleDamageTo: List<NamedResourceDto>?,
    @SerializedName("no_damage_from")
    val noDamageFrom: List<NamedResourceDto>?,
    @SerializedName("half_damage_from")
    val halfDamageFrom: List<NamedResourceDto>?,
    @SerializedName("double_damage_from")
    val doubleDamageFrom: List<NamedResourceDto>?
)

data class NamedResourceDto(
    val name: String,
    val url: String
)

