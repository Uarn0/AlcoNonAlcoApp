package com.example.cocktails.data.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDetailsDto(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String,
)

data class IngredientResponseDto(
    val ingredients: List<IngredientDetailsDto>?
)