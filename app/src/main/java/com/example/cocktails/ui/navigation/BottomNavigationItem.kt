package com.example.cocktails.ui.navigation

import com.example.cocktails.R
import com.example.cocktails.ui.Screens

data class BottomNavigationItem(
    val label: String = "",
    val icon: Int = R.drawable.alco,
    val route: String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem>{
        return listOf(
            BottomNavigationItem(
                label = "Alcoholic",
                icon = R.drawable.alco,
                route = Screens.Alcoholic.route
            ),
            BottomNavigationItem(
                label = "Non Alcoholic",
                icon = R.drawable.noalco,
                route = Screens.NonAlcoholic.route
            )
        )
    }
}