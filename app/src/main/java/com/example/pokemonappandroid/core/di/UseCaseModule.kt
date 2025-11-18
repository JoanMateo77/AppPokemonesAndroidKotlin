package com.example.pokemonappandroid.core.di

import com.example.pokemonappandroid.application.port.`in`.GetPokemonDetailUseCase
import com.example.pokemonappandroid.application.port.`in`.GetPokemonTypeUseCase
import com.example.pokemonappandroid.application.port.`in`.GetPokemonsUseCase
import com.example.pokemonappandroid.application.usecase.GetPokemonDetailUseCaseImpl
import com.example.pokemonappandroid.application.usecase.GetPokemonTypeUseCaseImpl
import com.example.pokemonappandroid.application.usecase.GetPokemonsUseCaseImpl
import org.koin.dsl.module

/**
 * Módulo de Koin para casos de uso
 */
val useCaseModule = module {
    factory<GetPokemonsUseCase> {
        GetPokemonsUseCaseImpl(get())
    }
    
    factory<GetPokemonDetailUseCase> {
        GetPokemonDetailUseCaseImpl(get())
    }
    
    factory<GetPokemonTypeUseCase> {
        GetPokemonTypeUseCaseImpl(get())
    }
}

