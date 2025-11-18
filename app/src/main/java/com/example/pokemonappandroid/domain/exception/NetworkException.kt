package com.example.pokemonappandroid.domain.exception

/**
 * Excepción de dominio para errores de red
 */
class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)

