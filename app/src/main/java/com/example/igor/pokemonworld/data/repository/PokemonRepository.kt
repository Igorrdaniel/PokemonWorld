package com.example.igor.pokemonworld.data.repository

import com.example.igor.pokemonworld.data.api.PokemonApiService
import com.example.igor.pokemonworld.data.model.PokemonDetail
import com.example.igor.pokemonworld.data.model.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val api: PokemonApiService) {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList = withContext(Dispatchers.IO) {
        api.getPokemonList(limit, offset)
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail = withContext(Dispatchers.IO) {
        api.getPokemonDetail(name)
    }
}