package com.example.pokemonappandroid.application.port.`in`

import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.Pokemon

/**
 * Interfaz del caso de uso para obtener Pokemons por tipo
 * Puerto de entrada (Inbound Port)
 */
interface GetPokemonTypeUseCase {
    suspend fun invoke(typeName: String): Result<List<Pokemon>>
}

