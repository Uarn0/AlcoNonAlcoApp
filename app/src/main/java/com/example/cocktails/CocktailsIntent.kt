package com.example.cocktails

sealed interface CocktailsIntent {
    data object Details : CocktailsIntent
    data object Ingredients : CocktailsIntent
    data object CopyToBuffer : CocktailsIntent
    data object Instruction : CocktailsIntent
    data object Measure : CocktailsIntent
}