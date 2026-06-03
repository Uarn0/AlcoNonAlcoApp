package com.example.cocktails

sealed interface CocktailsIntent {
    data class  Details(val idDrink: String) : CocktailsIntent
    data class Ingredient(val ingredient: String) : CocktailsIntent
}