package com.example.pokemonappandroid.application.port.`in`

import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.PokemonDetail

/**
 * Interfaz del caso de uso para obtener los detalles de un Pokemon
 * Puerto de entrada (Inbound Port)
 */
interface GetPokemonDetailUseCase {
    suspend fun invoke(id: Int): Result<PokemonDetail>
}

