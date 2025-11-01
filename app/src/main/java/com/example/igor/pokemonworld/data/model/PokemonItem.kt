package com.example.igor.pokemonworld.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonItem(
    val name: String,
    val url: String
){
    val id: Int get() = url.split("/").dropLast(1).last().toInt()
}
