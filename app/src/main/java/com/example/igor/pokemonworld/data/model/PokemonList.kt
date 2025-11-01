package com.example.igor.pokemonworld.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonItem>
)
