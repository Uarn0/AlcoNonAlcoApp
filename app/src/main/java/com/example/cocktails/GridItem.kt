package com.example.cocktails

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktails.data.retrofit.DrinkDvo

@Composable
fun GridItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedContentScope,
    item: DrinkDvo,
    screenType: String,
    onClick: () -> Unit,
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .clickable { onClick() }
                .padding(5.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${item.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ).fillMaxSize()

            ) {
                AsyncImage(
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = "image-$screenType-${item.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ).aspectRatio(1f),
                    model = item.imageUrl,
                    contentDescription = "DrinkImage",
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "text-$screenType-${item.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        ), text = item.name
                )
            }

        }
    }

}