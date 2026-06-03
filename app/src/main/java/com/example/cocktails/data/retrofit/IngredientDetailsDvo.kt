package com.example.cocktails.data.retrofit

data class IngredientDetailsDvo(
    val name: String? = null,
    val id: String? = null,
    val description: String? = null,
    val imageUrl: String? = "https://www.thecocktaildb.com/images/ingredients/$name.png",
)
