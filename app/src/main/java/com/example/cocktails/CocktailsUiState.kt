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

    val openedViaRandom: Boolean = false,
    val navigateToDetails: String? = null,
    val isRandomNavigation: Boolean = false,

    val isHomeLoading: Boolean = false,
    val homeError: String? = null,

    val isDetailsLoading: Boolean = false,
    val detailsError: String? = null,

    val isSearchActive: Boolean = false,

    val isIngredientLoading: Boolean = false,
    val ingredientError: String? = null,

    val searchResults: List<DrinkDvo> = emptyList(),
    val searchQuery: String = "",

    val isSearchLoading: Boolean = false,
    val searchError: String? = null
)