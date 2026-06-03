package com.example.cocktails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
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
import com.example.cocktails.viewmodel.CocktailsViewModel
import com.example.cocktails.GridItem

@Composable
fun AlcoholicScreen(
    vm: CocktailsViewModel,
    onCocktailClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedContentScope,
) {
    val state by vm.uiState.collectAsState()

    val drinks = state.drinkAlco


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(items = drinks, key = { drink -> drink.id }) { drink ->
                GridItem(
                    sharedTransitionScope,
                    animatedVisibilityScope,
                    item = drink,
                    onClick = { onCocktailClick(drink.id) },
                    screenType = "alco"
                )
            }
        }
    }


}

