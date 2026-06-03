package com.example.cocktails.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Square
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cocktails.ui.Screens

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Circle,
    val route: String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem>{
        return listOf(
            BottomNavigationItem(
                label = "Alcoholic",
                icon = Icons.Filled.Circle,
                route = Screens.Alcoholic.route
            ),
            BottomNavigationItem(
                label = "Non Alcoholic",
                icon = Icons.Filled.Square,
                route = Screens.NonAlcoholic.route
            )
        )
    }
}