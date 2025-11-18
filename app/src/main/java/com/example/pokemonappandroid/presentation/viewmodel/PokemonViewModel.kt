package com.example.pokemonappandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonappandroid.application.port.`in`.GetPokemonDetailUseCase
import com.example.pokemonappandroid.application.port.`in`.GetPokemonTypeUseCase
import com.example.pokemonappandroid.application.port.`in`.GetPokemonsUseCase
import com.example.pokemonappandroid.core.util.Result
import com.example.pokemonappandroid.presentation.event.PokemonEvent
import com.example.pokemonappandroid.presentation.state.PokemonDetailUiState
import com.example.pokemonappandroid.presentation.state.PokemonListState
import com.example.pokemonappandroid.presentation.state.PokemonTypeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel principal para manejar el estado de las pantallas de Pokemon
 */
class PokemonViewModel(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonTypeUseCase: GetPokemonTypeUseCase
) : ViewModel() {

    // Estado para la lista con paginación
    private val _pokemonListPaginatedState = MutableStateFlow(PokemonListState())
    val pokemonListPaginatedState: StateFlow<PokemonListState> = _pokemonListPaginatedState.asStateFlow()

    // Estado para el detalle de Pokemon
    private val _pokemonDetailState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val pokemonDetailState: StateFlow<PokemonDetailUiState> = _pokemonDetailState.asStateFlow()

    // Estado para Pokemons por tipo
    private val _pokemonTypeState = MutableStateFlow<PokemonTypeUiState>(PokemonTypeUiState.Loading)
    val pokemonTypeState: StateFlow<PokemonTypeUiState> = _pokemonTypeState.asStateFlow()

    init {
        loadPokemons()
    }

    /**
     * Maneja los eventos de UI
     */
    fun onEvent(event: PokemonEvent) {
        when (event) {
            is PokemonEvent.LoadMorePokemons -> loadMorePokemons(event.offset)
            is PokemonEvent.LoadPokemonDetail -> loadPokemonDetail(event.id)
            is PokemonEvent.LoadPokemonsByType -> loadPokemonsByType(event.typeName)
            is PokemonEvent.Refresh -> refresh()
            is PokemonEvent.Retry -> retry()
        }
    }

    /**
     * Carga la lista inicial de Pokemons
     */
    private fun loadPokemons() {
        viewModelScope.launch {
            _pokemonListPaginatedState.value = PokemonListState(isLoading = true)
            when (val result = getPokemonsUseCase.invoke(0, 20)) {
                is Result.Success -> {
                    _pokemonListPaginatedState.value = PokemonListState(
                        pokemons = result.data,
                        currentOffset = 20,
                        hasMore = result.data.isNotEmpty()
                    )
                }
                is Result.Error -> {
                    _pokemonListPaginatedState.value = PokemonListState(
                        error = result.exception.message ?: "Error desconocido"
                    )
                }
                is Result.Loading -> {
                    _pokemonListPaginatedState.value = PokemonListState(isLoading = true)
                }
            }
        }
    }

    /**
     * Carga más Pokemons (paginación)
     */
    private fun loadMorePokemons(offset: Int) {
        viewModelScope.launch {
            _pokemonListPaginatedState.value = _pokemonListPaginatedState.value.copy(isLoading = true)
            when (val result = getPokemonsUseCase.invoke(offset, 20)) {
                is Result.Success -> {
                    val currentPokemons = _pokemonListPaginatedState.value.pokemons
                    val newPokemons = currentPokemons + result.data
                    _pokemonListPaginatedState.value = PokemonListState(
                        pokemons = newPokemons,
                        isLoading = false,
                        currentOffset = offset + 20,
                        hasMore = result.data.isNotEmpty()
                    )
                }
                is Result.Error -> {
                    _pokemonListPaginatedState.value = _pokemonListPaginatedState.value.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Error desconocido"
                    )
                }
                is Result.Loading -> {
                    _pokemonListPaginatedState.value = _pokemonListPaginatedState.value.copy(isLoading = true)
                }
            }
        }
    }

    /**
     * Carga los detalles de un Pokemon
     */
    private fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            _pokemonDetailState.value = PokemonDetailUiState.Loading
            when (val result = getPokemonDetailUseCase.invoke(id)) {
                is Result.Success -> {
                    _pokemonDetailState.value = PokemonDetailUiState.Success(result.data)
                }
                is Result.Error -> {
                    _pokemonDetailState.value = PokemonDetailUiState.Error(
                        result.exception.message ?: "Error desconocido"
                    )
                }
                is Result.Loading -> {
                    _pokemonDetailState.value = PokemonDetailUiState.Loading
                }
            }
        }
    }

    /**
     * Carga Pokemons por tipo
     */
    private fun loadPokemonsByType(typeName: String) {
        viewModelScope.launch {
            _pokemonTypeState.value = PokemonTypeUiState.Loading
            when (val result = getPokemonTypeUseCase.invoke(typeName)) {
                is Result.Success -> {
                    _pokemonTypeState.value = PokemonTypeUiState.Success(result.data)
                }
                is Result.Error -> {
                    _pokemonTypeState.value = PokemonTypeUiState.Error(
                        result.exception.message ?: "Error desconocido"
                    )
                }
                is Result.Loading -> {
                    _pokemonTypeState.value = PokemonTypeUiState.Loading
                }
            }
        }
    }

    /**
     * Refresca la lista de Pokemons
     */
    private fun refresh() {
        loadPokemons()
    }

    /**
     * Reintenta la última operación
     */
    private fun retry() {
        val listState = _pokemonListPaginatedState.value
        val typeState = _pokemonTypeState.value

        if (listState.error != null) {
            loadPokemons()
        } else if (typeState is PokemonTypeUiState.Error) {
            // No hay una forma directa de obtener el `typeName` aquí, así que reintentamos la carga inicial.
            loadPokemons()
        }
    }
}
