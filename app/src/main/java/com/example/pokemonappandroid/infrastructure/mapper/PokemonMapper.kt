package com.example.pokemonappandroid.infrastructure.mapper

import com.example.pokemonappandroid.domain.model.Pokemon
import com.example.pokemonappandroid.domain.model.PokemonDetail
import com.example.pokemonappandroid.domain.model.PokemonStat
import com.example.pokemonappandroid.domain.model.PokemonType
import com.example.pokemonappandroid.domain.model.PokemonAbility
import com.example.pokemonappandroid.infrastructure.dto.PokemonDetailDto
import com.example.pokemonappandroid.infrastructure.dto.PokemonListItemDto

/**
 * Mapper para convertir DTOs a entidades de dominio
 */
object PokemonMapper {
    
    /**
     * Extrae el ID del Pokemon desde la URL
     * Ejemplo: "https://pokeapi.co/api/v2/pokemon/1/" -> 1
     */
    private fun extractIdFromUrl(url: String): Int {
        return url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0
    }
    
    /**
     * Genera la URL de la imagen del Pokemon
     */
    private fun getImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
    
    /**
     * Convierte un PokemonListItemDto a Pokemon
     */
    fun toDomain(dto: PokemonListItemDto): Pokemon {
        val id = extractIdFromUrl(dto.url)
        return Pokemon(
            id = id,
            name = dto.name.replaceFirstChar { it.uppercaseChar() },
            imageUrl = getImageUrl(id),
            types = emptyList() // Se obtendrán en el detalle
        )
    }
    
    /**
     * Convierte un PokemonDetailDto a PokemonDetail
     */
    fun toDomain(dto: PokemonDetailDto): PokemonDetail {
        return PokemonDetail(
            id = dto.id,
            name = dto.name.replaceFirstChar { it.uppercaseChar() },
            imageUrl = dto.sprites.frontDefault ?: getImageUrl(dto.id),
            imageUrlShiny = dto.sprites.frontShiny,
            imageUrlBack = dto.sprites.backDefault,
            baseExperience = dto.baseExperience,
            height = dto.height,
            weight = dto.weight,
            order = dto.order,
            types = dto.types.map { typeSlot ->
                PokemonType(
                    name = typeSlot.type.name.replaceFirstChar { it.uppercaseChar() },
                    slot = typeSlot.slot
                )
            },
            stats = dto.stats.map { stat ->
                PokemonStat(
                    name = stat.stat.name.replace("-", " ").replaceFirstChar { it.uppercaseChar() },
                    baseStat = stat.baseStat,
                    effort = stat.effort
                )
            },
            abilities = dto.abilities.map { abilitySlot ->
                PokemonAbility(
                    name = abilitySlot.ability.name.replaceFirstChar { it.uppercaseChar() },
                    isHidden = abilitySlot.isHidden,
                    slot = abilitySlot.slot
                )
            }
        )
    }
    
    /**
     * Convierte una lista de PokemonListItemDto a lista de Pokemon
     */
    fun toDomainList(dtoList: List<PokemonListItemDto>): List<Pokemon> {
        return dtoList.map { toDomain(it) }
    }
}
