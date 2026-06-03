package com.example.cocktails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cocktails.R
import com.example.cocktails.viewmodel.CocktailsViewModel

@Composable
fun IngredientDetailsScreen(
    vm: CocktailsViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedContentScope,
    passedIngredientName: String,
) {

    with(sharedTransitionScope) {
        val state by vm.uiState.collectAsState()
        val ingredient = state.ingredient
        val mostPopDrink = state.allTypeDrinks

        val safeKey = passedIngredientName.lowercase().trim()
        val currentNameInState = ingredient?.name?.lowercase()?.trim()
        val isDataFresh = currentNameInState == safeKey

//        val lowResUrl = "https://www.thecocktaildb.com/images/ingredients/$passedIngredientName-Medium.png"
//
//        val highResUrl = if (isDataFresh && ingredient?.imageUrl != null) {
//            ingredient.imageUrl
//        } else {
//            "https://www.thecocktaildb.com/images/ingredients/$passedIngredientName.png"
//        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                if (ingredient?.imageUrl != null) {
                    AsyncImage(
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image-${passedIngredientName}"),
                                animatedVisibilityScope
                            )
                            .fillMaxSize()
                            .height(300.dp)
                            .clip(RoundedCornerShape(percent = 10)),
//                        placeholder = rememberAsyncImagePainter(model = lowResUrl),
//                        fallback = rememberAsyncImagePainter(model = lowResUrl),
                        model = "https://www.thecocktaildb.com/images/ingredients/$passedIngredientName.png",
                        contentDescription = "ingredient: $passedIngredientName"
                    )
                }
            }

            item {
                if (ingredient != null) {
                    Text(
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "ingredient-${passedIngredientName}"),
                            animatedVisibilityScope
                        ),
                        text = if (isDataFresh) ingredient.name
                            ?: passedIngredientName else passedIngredientName
                    )
                }
            }
            item {
                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(RoundedCornerShape(percent = 10)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.size(
                        height = 10.dp,
                        width = 0.dp
                    ))
                    if (mostPopDrink != null) {
                        Text("The most popular drink made with this ingredient:")
                        AsyncImage(
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "image-${mostPopDrink.id}"),
                                    animatedVisibilityScope
                                )
                                .fillMaxSize()
                                .height(400.dp)
                                .clip(RoundedCornerShape(percent = 10)),
                            placeholder = painterResource(R.drawable.gray_square),
                            model = mostPopDrink.imageUrl,
                            contentDescription = "Most popular drink with that ingredient ${mostPopDrink.name}"
                        )
                        Text(
                            text = "Drink name: ${mostPopDrink.name}"
                        )
                    }
                    Spacer(modifier = Modifier.size(
                        height = 10.dp,
                        width = 0.dp
                    ))
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
}