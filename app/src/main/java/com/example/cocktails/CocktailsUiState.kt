package com.example.cocktails

import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDvo
import com.example.cocktails.data.retrofit.IngredientDetailsDvo

data class CocktailsUiState(
    val drinksNonAlco: List<DrinkDvo> = emptyList(),
    val drinkAlco: List<DrinkDvo> = emptyList(),
    val allTypeDrinks: DrinkDvo? = null,
    val details: DrinkDetailsDvo? = null,
    val ingredient: IngredientDetailsDvo? = null,
    val uiIsReady: Boolean = true
)
