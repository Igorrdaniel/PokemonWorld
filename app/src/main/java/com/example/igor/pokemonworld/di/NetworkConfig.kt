package com.example.igor.pokemonworld.di

import android.content.Context
import com.example.igor.pokemonworld.data.api.PokemonApiService
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File

object NetworkConfig {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun provideApiService(context: Context): PokemonApiService {
        val cacheSize = 10L * 1024 * 1024
        val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType)).build()


        return retrofit.create(PokemonApiService::class.java)
    }
}