package com.example.igor.pokemonworld.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
)
