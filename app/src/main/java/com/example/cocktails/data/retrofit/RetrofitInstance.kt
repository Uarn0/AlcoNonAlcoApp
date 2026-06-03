package com.example.cocktails.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val ALCO = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic"
    private const val NON_ALCO = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic"
    private const val FULL_DETAILS = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=0"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(ALCO).addConverterFactory(GsonConverterFactory.create()).build()
    }
}