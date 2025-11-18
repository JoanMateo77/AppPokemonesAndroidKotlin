package com.example.pokemonappandroid.application.port.`in`

import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.Pokemon

/**
 * Interfaz del caso de uso para obtener la lista de Pokemons
 * Puerto de entrada (Inbound Port)
 */
interface GetPokemonsUseCase {
    suspend fun invoke(offset: Int = 0, limit: Int = 20): Result<List<Pokemon>>
}

