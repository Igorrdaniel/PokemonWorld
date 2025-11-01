package com.example.igor.pokemonworld.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igor.pokemonworld.data.model.PokemonDetail
import com.example.igor.pokemonworld.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val repository: PokemonRepository) : ViewModel() {

   private val _detail = mutableStateOf<PokemonDetail?>(null)
    val detail: State<PokemonDetail?> = _detail

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadDetail(name: String) {
        viewModelScope.launch {
            try {
                _detail.value = repository.getPokemonDetail(name)
            } catch (e: Exception) {
                _error.value = "Erro ao carregar detalhes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}