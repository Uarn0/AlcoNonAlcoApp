package com.example.cocktails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.cocktails.GridItem
import com.example.cocktails.viewmodel.CocktailsViewModel

@Composable
fun NonAlcoholicScreen(
    vm: CocktailsViewModel,
    onCocktailClick: (String) -> Unit
) {

    val state by vm.uiState.collectAsState()

    val drinks = state.drinksNonAlco

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(drinks) { drink ->
                GridItem(drink, onClick = {onCocktailClick(drink.id)})
            }
        }
    }
}