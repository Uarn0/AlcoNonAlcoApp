package com.example.cocktails

import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDvo

data class CocktailsUiState(
    val drinksNonAlco: List<DrinkDvo> = emptyList(),
    val drinkAlco: List<DrinkDvo> = emptyList(),
    val details: DrinkDetailsDvo? = null,
    val selectedIngredient: String? = null,
    val uiIsReady: Boolean = true
)
