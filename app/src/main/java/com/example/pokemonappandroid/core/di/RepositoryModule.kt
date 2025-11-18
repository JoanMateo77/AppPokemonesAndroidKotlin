package com.example.pokemonappandroid.core.di

import com.example.pokemonappandroid.application.port.out.PokemonRepository
import com.example.pokemonappandroid.infrastructure.repository.PokemonRepositoryImpl
import org.koin.dsl.module

/**
 * Módulo de Koin para repositorios
 */
val repositoryModule = module {
    single<PokemonRepository> {
        PokemonRepositoryImpl(get())
    }
}

