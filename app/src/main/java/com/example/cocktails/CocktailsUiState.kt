package com.example.cocktails

data class CocktailsUiState(
    val type: String = "",
    val name: String = "",
    val category: String = "",
    val ingredient: List<String>,
    val instruction: String = "",
    val measure: List<String>,
    val idDrink: Int? = null,
    val imageDrink: String = ""
)
