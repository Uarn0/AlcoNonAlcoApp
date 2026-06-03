package com.example.cocktails.data.repository

import com.example.cocktails.data.retrofit.DrinkDetailsDto
import com.example.cocktails.data.service.CocktailsApiService
import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDto
import com.example.cocktails.data.retrofit.DrinkDvo
import com.example.cocktails.data.retrofit.IngredientDetailsDto
import com.example.cocktails.data.retrofit.IngredientDetailsDvo
import com.example.cocktails.data.toDvo
import com.example.cocktails.data.toDvoList

class CocktailsRetrofitRepository(private val api: CocktailsApiService) : CocktailsRepository {
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
            ?: throw Exception("The server found nothing")

        return singleDrinkDto.toDvo()
    }

    override suspend fun getAllDrinkByIngredient(id: String): DrinkDvo {
        val response = api.getCocktailsByIngredient(id)
        val singleDrink: DrinkDto =
            response.drinks.firstOrNull() ?: throw Exception("The server found nothing")
        return singleDrink.toDvo()
    }

    override suspend fun getIngredient(id: String): IngredientDetailsDvo {

        val response = api.getIngredient(id)

        val singleIngredient: IngredientDetailsDto =
            response.ingredients?.firstOrNull() ?: throw Exception("The server found nothing")

        return singleIngredient.toDvo()
    }



    override suspend fun getRandomDrink(): DrinkDetailsDvo {
        val response = api.getRandomDrink()

        val singleRandomDrinkDto: DrinkDetailsDto = response.drinks?.firstOrNull()
            ?: throw Exception("The server found nothing")

        return singleRandomDrinkDto.toDvo()
    }

    override suspend fun searchCocktails(query: String): List<DrinkDvo> {
        val response = api.searchCocktails(query)
        return response.drinks.map { it.toDvo() }
    }
}