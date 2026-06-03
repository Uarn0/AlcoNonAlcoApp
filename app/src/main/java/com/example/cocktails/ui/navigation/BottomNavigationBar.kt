package com.example.cocktails.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktails.ui.navigation.BottomNavigationItem
import com.example.cocktails.ui.AlcoholicScreen
import com.example.cocktails.ui.NonAlcoholicScreen
import com.example.cocktails.ui.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(item.label)
                    },
                    icon = {
                        Icon(item.icon, contentDescription = "${item.label} menu")
                    },
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Alcoholic.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Screens.Alcoholic.route) {
                AlcoholicScreen(navController)
                Log.d("nav", "in alco")
            }
            composable(Screens.NonAlcoholic.route) {
                NonAlcoholicScreen(navController)
                Log.d("nav", "in non alco")
            }
        }
    }

}