package com.example.pokemonappandroid.application.usecase

import com.example.pokemonappandroid.application.port.`in`.GetPokemonsUseCase
import com.example.pokemonappandroid.application.port.out.PokemonRepository
import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.Pokemon

/**
 * Implementación del caso de uso para obtener la lista de Pokemons
 */
class GetPokemonsUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonsUseCase {
    
    override suspend fun invoke(offset: Int, limit: Int): Result<List<Pokemon>> {
        return repository.getPokemonList(offset, limit)
    }
}

