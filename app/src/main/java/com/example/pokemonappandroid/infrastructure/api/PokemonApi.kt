package com.example.pokemonappandroid.infrastructure.api

import com.example.pokemonappandroid.infrastructure.dto.PokemonDetailDto
import com.example.pokemonappandroid.infrastructure.dto.PokemonListDto
import com.example.pokemonappandroid.infrastructure.dto.NamedResourceDto
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz Retrofit para la API de Pokemon
 */
interface PokemonApi {
    
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): PokemonListDto
    
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailDto
    
    @GET("type/{typeName}")
    suspend fun getPokemonType(
        @Path("typeName") typeName: String
    ): PokemonTypeDto
}

data class PokemonTypeDto(
    val id: Int,
    val name: String,
    @SerializedName("damage_relations")
    val damageRelations: com.example.pokemonappandroid.infrastructure.dto.TypeDamageRelationsDto?,
    val pokemon: List<TypePokemonDto>,
    val moves: List<NamedResourceDto>?
)

data class TypePokemonDto(
    val pokemon: TypePokemonNameDto
)

data class TypePokemonNameDto(
    val name: String,
    val url: String
)
