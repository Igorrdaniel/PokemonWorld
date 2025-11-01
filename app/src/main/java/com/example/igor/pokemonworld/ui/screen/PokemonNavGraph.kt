package com.example.igor.pokemonworld.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.igor.pokemonworld.ui.screen.route.Routes

@Composable
fun PokemonNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            PokemonListScreen(onPokemonClick = { name ->
                navController.navigate(Routes.DETAIL.replace("{name}", name))
            })
        }
        composable(Routes.DETAIL) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            PokemonDetailScreen(name = name, navController = navController)
        }
    }
}