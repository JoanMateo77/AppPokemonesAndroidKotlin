package com.example.pokemonappandroid.application.usecase

import com.example.pokemonappandroid.application.port.`in`.GetPokemonTypeUseCase
import com.example.pokemonappandroid.application.port.out.PokemonRepository
import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.Pokemon

/**
 * Implementación del caso de uso para obtener Pokemons por tipo
 */
class GetPokemonTypeUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonTypeUseCase {
    
    override suspend fun invoke(typeName: String): Result<List<Pokemon>> {
        return repository.getPokemonsByType(typeName.lowercase())
    }
}

