package com.example.pokemonappandroid

import android.app.Application
import com.example.pokemonappandroid.core.di.AppModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}
