package com.example.cocktails.data.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class DrinkDto(
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String,
)

data class DrinksDto(
    val drinks: List<DrinkDto>
)