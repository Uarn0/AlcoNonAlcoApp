package com.example.cocktails.data.retrofit

data class DrinkDetailsDvo(
    val id: String,
    val name: String,
    val instruction: String,
    val imageUrl: String,
    val ingredients: List<IngredientItem>
)

data class IngredientItem(
    val name: String,
    val imageUrl: String = "https://www.thecocktaildb.com/images/ingredients/${name}-Medium.png",
    val measure: String
)