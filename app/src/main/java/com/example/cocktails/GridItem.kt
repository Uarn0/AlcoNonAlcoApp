package com.example.cocktails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktails.data.retrofit.DrinkDvo

@Composable
fun GridItem(item: DrinkDvo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .padding(5.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = "DrinkImage"
        )
        Text(item.name)
    }
}