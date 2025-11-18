package com.example.pokemonappandroid.presentation.navigation

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonappandroid.presentation.ui.screens.DetailScreen
import com.example.pokemonappandroid.presentation.ui.screens.HomeScreen
import com.example.pokemonappandroid.presentation.ui.screens.TypesScreen
import com.example.pokemonappandroid.presentation.viewmodel.PokemonViewModel

/**
 * Definición de rutas de navegación
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    data class Detail(val id: Int = 0) : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    data class Types(val typeName: String = "") : Screen("types/{typeName}") {
        fun createRoute(typeName: String) = "types/$typeName"
    }
}

/**
 * Grafo de navegación de la aplicación
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: PokemonViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onPokemonClick = { id ->
                    navController.navigate(Screen.Detail().createRoute(id))
                },
                onTypesClick = {
                    // Por ahora navega a un tipo por defecto, se puede mejorar con un selector
                    navController.navigate(Screen.Types().createRoute("fire"))
                }
            )
        }
        
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(
                pokemonId = id,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(
            route = "types/{typeName}",
            arguments = listOf(navArgument("typeName") { type = NavType.StringType })
        ) { backStackEntry ->
            val typeName = backStackEntry.arguments?.getString("typeName") ?: ""
            TypesScreen(
                typeName = typeName,
                viewModel = viewModel,
                onPokemonClick = { id ->
                    navController.navigate(Screen.Detail().createRoute(id))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
