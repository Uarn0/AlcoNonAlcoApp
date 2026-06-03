package com.example.cocktails.data.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String
)

data class IngredientResponseDto(

)