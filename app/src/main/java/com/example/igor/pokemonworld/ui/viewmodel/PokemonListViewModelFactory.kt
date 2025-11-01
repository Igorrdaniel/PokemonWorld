package com.example.igor.pokemonworld.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.igor.pokemonworld.MyApplication
import com.example.igor.pokemonworld.data.repository.PokemonRepository
import com.example.igor.pokemonworld.di.NetworkConfig

class PokemonListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = NetworkConfig.provideApiService(MyApplication.appContext)
        val repo = PokemonRepository(api)
        return PokemonListViewModel(repo) as T
    }
}