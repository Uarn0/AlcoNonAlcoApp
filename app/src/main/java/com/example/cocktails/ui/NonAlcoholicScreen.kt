package com.example.cocktails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktails.CocktailsIntent
import com.example.cocktails.GridItem
import com.example.cocktails.viewmodel.CocktailsViewModel

@Composable
fun NonAlcoholicScreen(
    vm: CocktailsViewModel,
    onCocktailClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedContentScope,
) {

    val state by vm.uiState.collectAsState()

    when {
        state.isHomeLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.homeError != null -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(7.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(state.homeError!!)
                Button(onClick = { vm.onIntent(CocktailsIntent.LoadCocktails) }) {
                    Text("Retry")
                }
            }
        }

        else -> {
            val drinks = state.drinksNonAlco
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(items = drinks, key = { drink -> drink.id }) { drink ->
                        GridItem(
                            sharedTransitionScope, animatedVisibilityScope, drink,
                            screenType = "nonalco"
                        ) {
                            onCocktailClick(drink.id)
                        }
                    }
                }
            }
        }
    }
}