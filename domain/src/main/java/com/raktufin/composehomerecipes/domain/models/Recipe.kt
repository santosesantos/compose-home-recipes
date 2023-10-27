package com.raktufin.composehomerecipes.domain.models

typealias RecipeDomain = Recipe

data class Recipe(
    val id: Int = 0,
    val name: String
)