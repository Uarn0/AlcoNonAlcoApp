package com.example.cocktails.data.repository

import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDvo
import com.example.cocktails.data.retrofit.IngredientDetailsDvo

interface CocktailsRepository {
    suspend fun getAlcoGridInfo(): List<DrinkDvo>
    suspend fun getNonAlcoGridInfo(): List<DrinkDvo>
    suspend fun getDetails(id: String): DrinkDetailsDvo
    suspend fun getAllDrinkByIngredient(id : String): DrinkDvo

    suspend fun getIngredient(id: String): IngredientDetailsDvo
}
