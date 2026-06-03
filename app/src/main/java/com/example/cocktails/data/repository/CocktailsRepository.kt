package com.example.cocktails.data.repository

import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDto
import com.example.cocktails.data.retrofit.DrinkDvo

interface CocktailsRepository {
    suspend fun getAlcoGridInfo(): List<DrinkDvo>
    suspend fun getNonAlcoGridInfo(): List<DrinkDvo>
    suspend fun getDetails(id: String): DrinkDetailsDvo
}


//https://www.thecocktaildb.com/images/ingredients/Brown Sugar.png