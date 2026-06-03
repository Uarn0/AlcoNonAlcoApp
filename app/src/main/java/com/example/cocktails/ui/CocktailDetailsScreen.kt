package com.example.cocktails.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import com.example.cocktails.viewmodel.CocktailsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.IngredientItem

@Composable
fun CocktailDetailsScreen(vm: CocktailsViewModel, onIngredientClick: (String) -> Unit) {

    val state by vm.uiState.collectAsState()

    val details = state.details

//    if (details != null) CircularProgressIndicator() else return
    LazyColumn(
        Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item{
            AsyncImage(
                model = details?.imageUrl,
                contentDescription = "Image of ${details?.name}",
            )
        }
        item {
            if (details != null) {
                Text(details.name, style = MaterialTheme.typography.titleLarge)
            }
        }
        item{
            Text("Instruction:")
            Text("${details?.instruction}", style = MaterialTheme.typography.bodyLarge)
        }

        item {

        }

        item{
            Text("Ingredients:")
            if (details != null) {
                IngredientCard(details)
            }
        }

    }
}



@Composable
private fun IngredientCard(details: DrinkDetailsDvo) {

    val ingredients = details.ingredients


    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        items(ingredients) { ingredient ->
            Card(modifier = Modifier
                .fillMaxSize()
                .clickable { }
                .padding(5.dp),
                border = BorderStroke(.5.dp, Color.Black),
                shape = RoundedCornerShape(0.dp)) {
                Column {
                    AsyncImage(
                        model = ingredient.imageUrl,
                        contentDescription = "image of ${ingredient.name}"
                    )
                    Text(ingredient.name)
                    Text(ingredient.measure)
                }
            }
        }
    }
}
