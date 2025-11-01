package com.example.igor.pokemonworld.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.igor.pokemonworld.ui.viewmodel.PokemonDetailViewModel
import com.example.igor.pokemonworld.ui.viewmodel.PokemonDetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(name: String, navController: NavController) {
    val viewModel: PokemonDetailViewModel = viewModel(factory = PokemonDetailViewModelFactory())

    val detail by viewModel.detail
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    LaunchedEffect(name) {
        viewModel.loadDetail(name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Pokémon") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(error!!, color = Color.Red)
            } else if (detail != null) {
                AsyncImage(
                    model = detail!!.sprites.front_default,
                    contentDescription = "Imagem do Pokémon",
                    modifier = Modifier.size(200.dp)
                )
                Text("Name: ${detail!!.name.capitalize()}")
                Text("Height: ${detail!!.height} dm")
                Text("Weight: ${detail!!.weight} hg")
                Text("Types: ${detail!!.types.joinToString { it.type.name.capitalize() }}")
            }
        }
    }
}