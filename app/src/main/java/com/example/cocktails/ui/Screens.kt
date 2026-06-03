package com.example.cocktails.ui

sealed class Screens(val route: String) {
    object Alcoholic : Screens("alcoholic_screen")
    object NonAlcoholic : Screens("nonalcoholic_screen")
    object Details : Screens("detail_screen")
    object Ingredient : Screens("ingredient_screen")
}