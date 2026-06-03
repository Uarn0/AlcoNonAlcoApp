package com.example.cocktails.data.service

import com.example.cocktails.data.retrofit.DrinkDetailsResponseDto
import com.example.cocktails.data.retrofit.DrinksDto
import com.example.cocktails.data.retrofit.IngredientResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"


private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface CocktailsApiService {
    @GET("filter.php")
    suspend fun getGrid(@Query("a") drinkType: String): DrinksDto

    @GET("random.php")
    suspend fun getRandomDrink() : DrinkDetailsResponseDto

    @GET("lookup.php")
    suspend fun getDetail(
        @Query("i") id: String,
    ): DrinkDetailsResponseDto


    @GET("filter.php")
    suspend fun getCocktailsByIngredient(@Query("i") name: String): DrinksDto

    @GET("search.php")
    suspend fun getIngredient(@Query("i") id: String): IngredientResponseDto

    @GET("search.php")
    suspend fun searchCocktails(@Query("s") query: String): DrinksDto
}

object RetrofitInstance {
    val api: CocktailsApiService by lazy {
        retrofit.create(CocktailsApiService::class.java)
    }
}