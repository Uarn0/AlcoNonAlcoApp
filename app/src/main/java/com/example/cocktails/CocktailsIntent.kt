package com.example.cocktails

sealed interface CocktailsIntent {
    data class  Details(val idDrink: String) : CocktailsIntent
    data class Ingredient(val ingredient: String) : CocktailsIntent
    object RandomDrink : CocktailsIntent
    data class SearchDrink(val drinkName: String) : CocktailsIntent
    object LoadCocktails : CocktailsIntent
    object ClearNavigation : CocktailsIntent
    object ToggleSearch : CocktailsIntent
    object ClearSearch : CocktailsIntent
}