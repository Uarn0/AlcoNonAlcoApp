package com.example.cocktails.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktails.viewmodel.CocktailsViewModel

@Composable
fun IngredientDetailsScreen(vm: CocktailsViewModel) {
    val state by vm.uiState.collectAsState()
    val ingredient = state.ingredient
    val mostPopDrink = state.allTypeDrinks
    LazyColumn(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            if (ingredient?.imageUrl != null) {
                AsyncImage(
                    model = ingredient.imageUrl,
                    contentDescription = "ingredient: ${ingredient.name}"
                )
            }
        }
        item {
            if (ingredient != null) {
                Text(ingredient.name.toString())
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.size(30.dp))
                if (mostPopDrink != null) {
                    Text("The most popular drink made with this ingredient:")
                    AsyncImage(
                        model = mostPopDrink.imageUrl,
                        contentDescription = "Most popular drink with that ingredient ${mostPopDrink.name}"
                    )
                    Text("Drink name: ${mostPopDrink.name}")
                }
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
        item {

            if (ingredient?.description != null) {
                Text("Description")
                Text(ingredient.description)
            } else {
                Text("Do not need description")
            }
        }
    }
}