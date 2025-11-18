package com.example.pokemonappandroid.domain.model

/**
 * Modelo de dominio para un Pokemon en la lista
 * Representa la información básica de un Pokemon
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val baseExperience: Int? = null,
    val order: Int = 0
)
