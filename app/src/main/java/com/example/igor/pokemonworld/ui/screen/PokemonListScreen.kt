package com.example.igor.pokemonworld.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.igor.pokemonworld.ui.viewmodel.PokemonListViewModel
import com.example.igor.pokemonworld.ui.viewmodel.PokemonListViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(onPokemonClick: (String) -> Unit) {

    val viewModel: PokemonListViewModel = viewModel(factory = PokemonListViewModelFactory())

    val filteredList by viewModel.filteredList
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val searchQuery by viewModel.searchQuery

    val listState = rememberLazyListState()

    val isAtEnd =
        remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == filteredList.size - 1 } }

    LaunchedEffect(isAtEnd.value) {
        if (isAtEnd.value && !isLoading && error == null && searchQuery.isEmpty()) {
            viewModel.loadMore()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pokémon World") })
        }
    ) { innerPadding ->
        Column(  // Use Column para empilhar search + lista
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                label = { Text("Filtrar por nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)  // Abaixa mais (aumente top para descer mais)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (error != null) {
                    Text(error!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredList) { pokemon ->
                            Card(
                                onClick = { onPokemonClick(pokemon.name) },
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Text(
                                    "${pokemon.name.capitalize()} - ID: ${pokemon.id}",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        if (isLoading) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}