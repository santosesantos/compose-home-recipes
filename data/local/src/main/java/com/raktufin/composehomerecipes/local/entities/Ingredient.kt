package com.raktufin.composehomerecipes.local.entities

typealias IngredientEntity = Ingredient

data class Ingredient(
    val id: Int,
    val name: String,
    val recipeId: Int
)
