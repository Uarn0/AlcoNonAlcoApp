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
import com.example.cocktails.data.retrofit.DrinkDvo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class CocktailsViewModel(
    val drinkRepository: CocktailsRetrofitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailsUiState())
    val uiState = _uiState.asStateFlow()
    fun onIntent(intent: CocktailsIntent) {
        when (intent) {
            is CocktailsIntent.Details -> {
                getDetail(intent.idDrink)
            }

            is CocktailsIntent.Ingredient -> {
                getIngredient(intent.ingredient)
            }

            CocktailsIntent.LoadCocktails -> {
                loadCocktails()
            }

            CocktailsIntent.RandomDrink -> {
                getRandomDrink()
            }

            is CocktailsIntent.SearchDrink -> {
                searching(intent.drinkName)
            }

            CocktailsIntent.ClearNavigation -> _uiState.update {
                it.copy(navigateToDetails = null, isRandomNavigation = false)
            }
            CocktailsIntent.ToggleSearch -> _uiState.update {
                it.copy(isSearchActive = !it.isSearchActive, searchResults = emptyList(), searchQuery = "")
            }

            CocktailsIntent.ClearSearch -> _uiState.update {
                it.copy(isSearchActive = false, searchResults = emptyList(), searchQuery = "")
            }
        }
    }

    private fun searching(intent: String) {
        _uiState.update { it.copy(searchQuery = intent) }
        searchQuery.value = intent
    }
    private val searchQuery = MutableStateFlow("")

    private fun getRandomDrink() {
        viewModelScope.launch {
            _uiState.update { it.copy(isDetailsLoading = true, detailsError = null) }
            try {
                val randomDrink = drinkRepository.getRandomDrink()
                Log.d("RANDOM", "Got drink: ${randomDrink.name}")
                _uiState.update {
                    it.copy(
                        isDetailsLoading = false,
                        details = randomDrink,
                        navigateToDetails = randomDrink.id,
                        isRandomNavigation = true,
                        openedViaRandom = true
                    )
                }
            } catch (e: Exception) {
                Log.e("RANDOFM", "Error: ${e.message}")
                _uiState.update {
                    it.copy(isDetailsLoading = false, detailsError = e.message)
                }
            }
        }
    }

    private fun getDetail(idDrink: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isDetailsLoading = true, detailsError = null, details = null)
            }
            try {
                val drinkDetailsDvo = drinkRepository.getDetails(idDrink)
                _uiState.update {
                    it.copy(
                        isDetailsLoading = false,
                        openedViaRandom = false,
                        details = drinkDetailsDvo
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isDetailsLoading = false,
                        detailsError = "No Internet connection!"
                    )
                }
            }
        }
    }

    init {
        loadCocktails()
        Log.e("API", "Loaded")

        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.update { it.copy(searchResults = emptyList(), isSearchLoading = false) }
                    } else {
                        _uiState.update { it.copy(isSearchLoading = true, searchError = null) }
                        try {
                            val results = drinkRepository.searchCocktails(query)
                            _uiState.update {
                                it.copy(isSearchLoading = false, searchResults = results)
                            }
                        } catch (e: Exception) {
                            _uiState.update {
                                it.copy(isSearchLoading = false, searchError = "No Internet connection!")
                            }
                        }
                    }
                }
        }
    }
    private fun getIngredient(ingredient: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isIngredientLoading = true, ingredientError = null) }
            try {
                val loadedAllDrinks = drinkRepository.getAllDrinkByIngredient(ingredient)
                val loadedIngredient = drinkRepository.getIngredient(ingredient)
                _uiState.update {
                    it.copy(
                        isIngredientLoading = false,
                        allTypeDrinks = loadedAllDrinks,
                        ingredient = loadedIngredient
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isIngredientLoading = false, ingredientError = "No Internet connection!")
                }
            }
        }
    }

    fun loadCocktails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isHomeLoading = true, homeError = null) }
            try {
                val loadedAlco = drinkRepository.getAlcoGridInfo()
                val loadedNonAlco = drinkRepository.getNonAlcoGridInfo()
                _uiState.update {
                    it.copy(
                        isHomeLoading = false,
                        drinkAlco = loadedAlco,
                        drinksNonAlco = loadedNonAlco,
                        uiIsReady = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isHomeLoading = false, homeError = "No Internet connection!")
                }
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

}