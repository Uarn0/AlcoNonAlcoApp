package com.example.cocktails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    val drinks = state.drinksNonAlco

    Column(modifier = Modifier
        .fillMaxWidth()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = drinks, key = { drink -> drink.id }) { drink ->
                GridItem(
                    sharedTransitionScope, animatedVisibilityScope, drink,
                    screenType = "nonalco"
                ) {
                    onCocktailClick(
                        drink.id
                    )
                }
            }
        }
    }
}