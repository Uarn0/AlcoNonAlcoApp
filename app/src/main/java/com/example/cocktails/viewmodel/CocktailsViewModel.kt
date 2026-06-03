package com.example.cocktails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktails.CocktailApplication
import com.example.cocktails.CocktailsIntent
import com.example.cocktails.CocktailsUiState
import com.example.cocktails.data.repository.CocktailsRetrofitRepository
import com.example.cocktails.data.retrofit.AboutIngredientDto
import com.example.cocktails.data.service.RetrofitInstance
import com.example.cocktails.data.toDvo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailsViewModel(
    val drinkRepository: CocktailsRetrofitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailsUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: CocktailsIntent) {
        when (intent) {
            is CocktailsIntent.Details -> getDetail(intent.idDrink)
            is CocktailsIntent.Ingredient -> getIngredient(intent.ingredient)
        }
    }

    init {
        loadCocktails()
        Log.e("API", "Loaded")
    }

    private fun copyToBuffer(ingredients: AboutIngredientDto) {
        TODO("Not yet implemented")
    }

    private fun seeInstruction() {

    }

    private fun getIngredient(ingredient: String) {

    }

    private fun getDetail(idDrink: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDetail(idDrink)

                val drinkDetailsDvo = response.drinks?.firstOrNull()?.toDvo()

                if (drinkDetailsDvo != null) {
                    _uiState.update { it.copy(details = drinkDetailsDvo) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadCocktails() {
        viewModelScope.launch {
            try {
                val loadedAlco = drinkRepository.getAlcoGridInfo()
                val loadedNonAlco = drinkRepository.getNonAlcoGridInfo()

                _uiState.value = _uiState.value.copy(
                    drinkAlco = loadedAlco,
                    drinksNonAlco = loadedNonAlco
                )
                _uiState.value = _uiState.value.copy(
                    uiIsReady = false
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailApplication)
                CocktailsViewModel(drinkRepository = application.cocktailRepo)
            }
        }
    }

//    fun getInfoFromIngredient(ingredients: List<IngredientCard>): List<String>{
//
//        val temp = mutableListOf<String>()
//
//        for (measure in ingredients){
//            temp.add(measure.measure)
//        }
//        return temp
//    }


}