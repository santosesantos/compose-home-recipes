package com.raktufin.composehomerecipes.domain.models

typealias FullRecipeDomain = FullRecipe

data class FullRecipe(
    val recipe: Recipe,
    val ingredients: List<Ingredient>,
    val prepareModes: List<PrepareMode>
)
