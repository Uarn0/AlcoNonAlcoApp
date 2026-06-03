package com.example.cocktails.ui

import android.net.Uri
import android.view.WindowInsets
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cocktails.R

@Composable
fun CocktailDetailsScreen(
    vm: CocktailsViewModel,
    onIngredientClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        val state by vm.uiState.collectAsState()
        val basicDrink = vm.selectedBasicDrink.collectAsState().value
        val details = state.details
        if (basicDrink == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                AsyncImage(
//                    placeholder = painterResource(),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image-${basicDrink.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .fillMaxSize()
                        .height(300.dp)
                        .clip(RoundedCornerShape(percent = 10)),
                    model = basicDrink.imageUrl,
                    contentDescription = "Image of ${basicDrink.name}",
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Text(
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = "text-${basicDrink.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ), text = basicDrink.name, style = MaterialTheme.typography.titleLarge
                )
            }


            if (details == null) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(20.dp))
                }
            } else {
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
//                                        placeholder = painterResource(R.drawable.gray_square),
//                                        modifier = Modifier.sharedElement(
//                                            rememberSharedContentState(key = "image-${ingredient.name}"),
//                                            animatedVisibilityScope
//                                        ),
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
