package com.example.cocktails.data.repository

import com.example.cocktails.data.retrofit.DrinkDetailsDto
import com.example.cocktails.data.service.CocktailsApiService
import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDvo
import com.example.cocktails.data.toDvo
import com.example.cocktails.data.toDvoList

class CocktailsRetrofitRepository(private val api: CocktailsApiService): CocktailsRepository {
    override suspend fun getAlcoGridInfo(): List<DrinkDvo> {
        val response = api.getGrid("Alcoholic")

        return response.drinks.toDvoList()
    }

    override suspend fun getNonAlcoGridInfo(): List<DrinkDvo> {
        val response = api.getGrid("Non_Alcoholic")

        return response.drinks.toDvoList()
    }

    override suspend fun getDetails(id: String): DrinkDetailsDvo {
        val response = api.getDetail(id)

        val singleDrinkDto: DrinkDetailsDto = response.drinks?.firstOrNull()
            ?: throw Exception("Сервер нічого не знайшов")

        return singleDrinkDto.toDvo()
    }

//    override suspend fun getIngredients(): List<DetailInfo> {
//        TODO("Not yet implemented")
//    }
}