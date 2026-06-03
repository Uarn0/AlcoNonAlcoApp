package com.example.cocktails.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktails.CocktailsIntent
import com.example.cocktails.ui.AlcoholicScreen
import com.example.cocktails.ui.CocktailDetailsScreen
import com.example.cocktails.ui.NonAlcoholicScreen
import com.example.cocktails.ui.Screens
import com.example.cocktails.viewmodel.CocktailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(viewModel: CocktailsViewModel) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(item.label)
                    },
                    icon = {
                        Icon(item.icon, contentDescription = "${item.label} menu")
                    },
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Alcoholic.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Screens.Alcoholic.route) {
                AlcoholicScreen(
                    vm = viewModel,
                    onCocktailClick = { cocktailId ->
                        navController.navigate("${Screens.Details.route}/$cocktailId")
                    }
                )
            }

            composable(Screens.NonAlcoholic.route) {
                NonAlcoholicScreen(
                    vm = viewModel,
                    onCocktailClick = { cocktailId ->
                        navController.navigate("${Screens.Details.route}/$cocktailId")
                    },
                )
            }

            composable("${Screens.Details.route}/{id}") { backStackEntry ->
                val cocktailId = backStackEntry.arguments?.getString("id") ?: return@composable

                LaunchedEffect(key1 = cocktailId) {
                    viewModel.onIntent(CocktailsIntent.Details(cocktailId))
                }

                CocktailDetailsScreen(
                    vm = viewModel,
                    onIngredientClick = { id -> viewModel.onIntent(CocktailsIntent.Ingredient(id)) }
                )
            }

            composable("${Screens.Ingredient.route}/name") {backStackEntry ->
                val ingredientId = backStackEntry.arguments?.getString("name")?: return@composable

                LaunchedEffect(key1 = ingredientId) {
                    viewModel.onIntent((CocktailsIntent.Ingredient(ingredientId)))
                }
            }

        }
    }

}