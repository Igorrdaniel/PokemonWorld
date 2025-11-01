package com.example.igor.pokemonworld.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igor.pokemonworld.data.model.PokemonItem
import com.example.igor.pokemonworld.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonList = mutableStateOf<List<PokemonItem>>(emptyList())
    val pokemonList: State<List<PokemonItem>> = _pokemonList

    private val _filteredList = mutableStateOf<List<PokemonItem>>(emptyList())
    val filteredList: State<List<PokemonItem>> = _filteredList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var offset = 0
    private val limit = 20  // Page size (bonus paginate)

    init {
        loadMore()
    }

    fun loadMore() {
        if (_searchQuery.value.isNotEmpty()) return
        if (_isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getPokemonList(limit, offset)
                _pokemonList.value = _pokemonList.value + response.results
                applyFilter()
                offset += limit
            } catch (e: Exception) {
                _error.value = "Erro ao carregar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilter()
        _isLoading.value = false
    }

    private fun applyFilter() {
        val query = _searchQuery.value.lowercase()
        _filteredList.value = if (query.isEmpty()) {
            _pokemonList.value
        } else {
            _pokemonList.value.filter { it.name.lowercase().contains(query) }
        }
    }
}