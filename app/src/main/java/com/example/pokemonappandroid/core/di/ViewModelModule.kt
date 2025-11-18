package com.example.pokemonappandroid.core.di

import com.example.pokemonappandroid.presentation.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de Koin para ViewModels
 */
val viewModelModule = module {
    viewModel { PokemonViewModel(get(), get(), get()) }
}

