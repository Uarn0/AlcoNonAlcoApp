package com.example.cocktails.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import com.example.cocktails.viewmodel.CocktailsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.cocktails.CocktailsIntent

@Composable
fun CocktailDetailsScreen(
    vm: CocktailsViewModel,
    onIngredientClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    drink: String,
) {
    with(sharedTransitionScope) {
        val state by vm.uiState.collectAsState()
        when {
            state.isDetailsLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.detailsError != null -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(state.detailsError!!)
                    Button(onClick = { vm.onIntent(CocktailsIntent.Details(drink)) }) {
                        Text("Retry")
                    }
                }
            }

            else -> {
                val details = state.details ?: return
                val isRandom = state.openedViaRandom

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        AsyncImage(
                            modifier = Modifier.then(
                                if (!isRandom) Modifier.sharedElement(
                                    rememberSharedContentState(key = "image-${details.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                ) else Modifier
                            )
                                .fillMaxSize()
                                .height(300.dp)
                                .clip(RoundedCornerShape(percent = 10)),
                            model = details.imageUrl,
                            contentDescription = "Image of ${details.name}",
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {
                        Text(
                            modifier = Modifier.then(
                                if (!isRandom) Modifier.sharedElement(
                                    rememberSharedContentState(key = "text-${details.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                ) else Modifier
                            ),
                            text = details.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }


                    item {
                        Text("Instruction:")
                        Text(
                            modifier = Modifier
                                .padding(all = 10.dp),
                            text = details.instruction,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    item {
                        Text("Ingredients:")
                        val ingredients = details.ingredients


                        LazyRow(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(
                                items = ingredients,
                                key = { ingredient -> ingredient.name }) { ingredient ->
                                Card(
                                    modifier = Modifier
                                        .width(140.dp)
                                        .clickable {
                                            val safeRouteName = Uri.encode(ingredient.name)
                                            onIngredientClick(safeRouteName)}
                                        .padding(5.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.sharedElement(
                                            rememberSharedContentState(key = "image-${ingredient.name}"),
                                            animatedVisibilityScope
                                        ), horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = ingredient.imageUrl,
                                            contentDescription = "image of ${ingredient.name}"
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(5.dp),
                                            text = ingredient.name
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(5.dp),
                                            text = ingredient.measure
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
