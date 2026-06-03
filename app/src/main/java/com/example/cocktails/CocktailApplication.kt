package com.example.cocktails

import android.app.Application
import com.example.cocktails.data.repository.CocktailsRetrofitRepository
import com.example.cocktails.data.service.RetrofitInstance
import kotlin.getValue

class CocktailApplication : Application() {
    val cocktailRepo by lazy {
        CocktailsRetrofitRepository(RetrofitInstance.api)
    }

}