package com.example.pokemonappandroid.core.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Módulo principal de la aplicación
 * Configura Koin para inyección de dependencias
 */
object AppModule {
    fun init(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}

