package com.example.pokemonappandroid.application.usecase

import com.example.pokemonappandroid.application.port.`in`.GetPokemonDetailUseCase
import com.example.pokemonappandroid.application.port.out.PokemonRepository
import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.PokemonDetail

/**
 * Implementación del caso de uso para obtener los detalles de un Pokemon
 */
class GetPokemonDetailUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonDetailUseCase {
    
    override suspend fun invoke(id: Int): Result<PokemonDetail> {
        return repository.getPokemonDetail(id)
    }
}

