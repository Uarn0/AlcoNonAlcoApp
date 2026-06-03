package com.example.cocktails.data.repository

import com.example.cocktails.data.retrofit.DetailInfo
import com.example.cocktails.data.retrofit.GridInfo
import com.example.cocktails.data.retrofit.InstructionInfo
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {
    fun getGridInfo(): Flow<List<GridInfo>>
    fun getIngredients(): Flow<List<DetailInfo>>
    fun getInstruction(): Flow<InstructionInfo>
}