package com.example.cocktails.data

import com.example.cocktails.data.retrofit.DrinkDetailsDto
import com.example.cocktails.data.retrofit.DrinkDetailsDvo
import com.example.cocktails.data.retrofit.DrinkDto
import com.example.cocktails.data.retrofit.DrinkDvo
import com.example.cocktails.data.retrofit.IngredientItem

fun DrinkDto.toDvo(): DrinkDvo {
    return DrinkDvo(
        name = this.strDrink,
        imageUrl = this.strDrinkThumb,
        id = this.idDrink
    )
}

fun List<DrinkDto>.toDvoList(): List<DrinkDvo> {
    return this.map { it.toDvo() }
}

fun DrinkDetailsDto.toDvo(): DrinkDetailsDvo{

    val ingredients = listOfNotNull(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
    ).filter { it.isNotBlank() }

    val measures = listOfNotNull(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15
    ).filter { it.isNotBlank() }

    val pairedIngredients = ingredients.zip(measures) { ingredient, measure ->
        IngredientItem(name = ingredient, measure = measure)
    }
    return DrinkDetailsDvo(
        id = this.idDrink,
        name = this.strDrink,
        instruction = this.strInstructions,
        imageUrl = this.strDrinkThumb,
        ingredients = pairedIngredients
    )
}
