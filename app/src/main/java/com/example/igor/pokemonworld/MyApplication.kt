package com.example.igor.pokemonworld

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext  // Define cedo no ciclo de vida do app
    }
}