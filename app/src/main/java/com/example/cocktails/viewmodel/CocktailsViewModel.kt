package com.example.cocktails.viewmodel

import com.example.cocktails.CocktailsUiState
import com.example.cocktails.data.repository.CocktailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CocktailsViewModel(
    private val drinkRepository: CocktailsRepository
) {
    private val _uiState = MutableStateFlow(CocktailsUiState())
    val uiState = _uiState.asStateFlow()

    val gridInfo = drinkRepository.getGridInfo()


}