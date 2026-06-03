package com.example.cocktails.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktails.CocktailsIntent
import com.example.cocktails.ExpandableActionMenu
import com.example.cocktails.ui.AlcoholicScreen
import com.example.cocktails.ui.CocktailDetailsScreen
import com.example.cocktails.ui.IngredientDetailsScreen
import com.example.cocktails.ui.NonAlcoholicScreen
import com.example.cocktails.ui.Screens
import com.example.cocktails.ui.SearchBar
import com.example.cocktails.viewmodel.CocktailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(viewModel: CocktailsViewModel) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(state.navigateToDetails) {
        if (state.isRandomNavigation && state.navigateToDetails != null) {
            navController.navigate("${Screens.Details.route}/${state.navigateToDetails}")
            viewModel.onIntent(CocktailsIntent.ClearNavigation)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        ExpandableActionMenu(

            onRandomClick = { viewModel.onIntent(CocktailsIntent.RandomDrink) },
            onSearchClick = {
                viewModel.onIntent(CocktailsIntent.ToggleSearch)
            }
        )
    }, topBar = {
        if (state.isSearchActive) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onIntent(CocktailsIntent.SearchDrink(it)) },
                onClose = { viewModel.onIntent(CocktailsIntent.ClearSearch) },
                isLoading = state.isSearchLoading,
                results = state.searchResults,
                onResultClick = { drinkId ->
                    viewModel.onIntent(CocktailsIntent.ClearSearch)
                    viewModel.onIntent(CocktailsIntent.Details(drinkId))
                    navController.navigate("${Screens.Details.route}/$drinkId")
                }
            )
        }
    }, bottomBar = {

        NavigationBar {
            BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(item.label)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(36.dp)
                        )
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

        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = Screens.Alcoholic.route,
                modifier = Modifier.padding(paddingValues),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {

                composable(
                    Screens.Alcoholic.route,
                    enterTransition = { fadeIn(animationSpec = tween(6000)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(600)) },
                    popExitTransition = { fadeOut(animationSpec = tween(300)) }
                ) {

                    AlcoholicScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                        vm = viewModel,
                        onCocktailClick = { cocktailId ->
                            navController.navigate("${Screens.Details.route}/$cocktailId")
                        }
                    )
                }

                composable(
                    Screens.NonAlcoholic.route,
                    enterTransition = { fadeIn(animationSpec = tween(600)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(600)) },
                    popExitTransition = { fadeOut(animationSpec = tween(300)) }
                ) {

                    NonAlcoholicScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                        vm = viewModel,
                        onCocktailClick = { cocktailId ->
                            navController.navigate("${Screens.Details.route}/$cocktailId")
                        },
                    )
                }

                composable(
                    "${Screens.Details.route}/{id}",
                    enterTransition = { fadeIn(animationSpec = tween(600)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(600)) },
                    popExitTransition = { fadeOut(animationSpec = tween(300)) }
                ) { backStackEntry ->
                    val cocktailId = backStackEntry.arguments?.getString("id") ?: return@composable

                    LaunchedEffect(key1 = cocktailId) {
                        viewModel.onIntent(CocktailsIntent.Details(cocktailId))
                    }

                    CocktailDetailsScreen(
                        vm = viewModel,
                        drink = cocktailId,
                        onIngredientClick = { id -> navController.navigate("${Screens.Ingredient.route}/$id") },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                    )
                }

                composable(
                    "${Screens.Ingredient.route}/{name}",
                    enterTransition = { fadeIn(animationSpec = tween(1500)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(1500)) },
                    popExitTransition = { fadeOut(animationSpec = tween(300)) }
                ) { backStackEntry ->
                    val passedName =
                        backStackEntry.arguments?.getString("name") ?: return@composable

                    LaunchedEffect(key1 = passedName) {
                        viewModel.onIntent((CocktailsIntent.Ingredient(passedName)))
                    }

                    IngredientDetailsScreen(
                        viewModel,
                        passedIngredientName = passedName,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable,
                    )
                }

            }
        }
    }

}