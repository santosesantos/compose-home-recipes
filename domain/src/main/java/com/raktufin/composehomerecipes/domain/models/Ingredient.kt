package com.raktufin.composehomerecipes.domain.models

typealias IngredientDomain = Ingredient

data class Ingredient(
    val id: Int = 0,
    val name: String,
    val recipeId: Int
)