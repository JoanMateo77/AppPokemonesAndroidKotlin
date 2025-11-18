package com.example.pokemonappandroid.infrastructure.repository

import com.example.pokemonappandroid.application.port.out.PokemonRepository
import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.exception.NetworkException
import com.example.pokemonappandroid.domain.model.Pokemon
import com.example.pokemonappandroid.domain.model.PokemonDetail
import com.example.pokemonappandroid.infrastructure.api.PokemonApi
import com.example.pokemonappandroid.infrastructure.mapper.PokemonMapper
import java.io.IOException

/**
 * Implementación del repositorio de Pokemon
 * Adapter de salida (Outbound Adapter)
 */
class PokemonRepositoryImpl(
    private val api: PokemonApi
) : PokemonRepository {
    
    override suspend fun getPokemonList(offset: Int, limit: Int): Result<List<Pokemon>> {
        return try {
            val response = api.getPokemonList(offset, limit)
            val pokemons = PokemonMapper.toDomainList(response.results)
            Result.Success(pokemons)
        } catch (e: IOException) {
            Result.Error(NetworkException("Error de conexión: ${e.message}", e))
        } catch (e: Exception) {
            Result.Error(NetworkException("Error al obtener la lista: ${e.message}", e))
        }
    }
    
    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> {
        return try {
            val dto = api.getPokemonDetail(id)
            val pokemonDetail = PokemonMapper.toDomain(dto)
            Result.Success(pokemonDetail)
        } catch (e: IOException) {
            Result.Error(NetworkException("Error de conexión: ${e.message}", e))
        } catch (e: Exception) {
            Result.Error(NetworkException("Error al obtener el detalle: ${e.message}", e))
        }
    }
    
    override suspend fun getPokemonsByType(typeName: String): Result<List<Pokemon>> {
        return try {
            val typeResponse = api.getPokemonType(typeName)
            val pokemons = typeResponse.pokemon.map { typePokemon ->
                val id = extractIdFromUrl(typePokemon.pokemon.url)
                Pokemon(
                    id = id,
                    name = typePokemon.pokemon.name.replaceFirstChar { it.uppercaseChar() },
                    imageUrl = getImageUrl(id),
                    types = listOf(typeName.replaceFirstChar { it.uppercaseChar() })
                )
            }
            Result.Success(pokemons)
        } catch (e: IOException) {
            Result.Error(NetworkException("Error de conexión: ${e.message}", e))
        } catch (e: Exception) {
            Result.Error(NetworkException("Error al obtener Pokemons por tipo: ${e.message}", e))
        }
    }
    
    private fun extractIdFromUrl(url: String): Int {
        return url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0
    }
    
    private fun getImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}
