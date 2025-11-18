package com.example.pokemonappandroid.application.port.out

import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.domain.model.Pokemon
import com.example.pokemonappandroid.domain.model.PokemonDetail

/**
 * Interfaz del repositorio de Pokemon (Puerto de salida)
 * Define los contratos sin importar cómo se implementan
 */
interface PokemonRepository {
    
    /**
     * Obtiene la lista de Pokemons
     * @param offset Número de resultados a saltar
     * @param limit Cantidad de resultados a obtener
     */
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): Result<List<Pokemon>>
    
    /**
     * Obtiene los detalles de un Pokemon por ID
     * @param id ID del Pokemon
     */
    suspend fun getPokemonDetail(id: Int): Result<PokemonDetail>
    
    /**
     * Obtiene los Pokemons de un tipo específico
     * @param typeName Nombre del tipo (ej: "fire", "water")
     */
    suspend fun getPokemonsByType(typeName: String): Result<List<Pokemon>>
}

