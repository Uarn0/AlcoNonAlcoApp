package com.example.cocktails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cocktails.viewmodel.CocktailsViewModel
import com.example.cocktails.GridItem

@Composable
fun AlcoholicScreen(navController: NavHostController) {
    val nonAlco = listOf<String>()

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(nonAlco) { item ->
                GridItem(
                )
            }
        }
    }
}

