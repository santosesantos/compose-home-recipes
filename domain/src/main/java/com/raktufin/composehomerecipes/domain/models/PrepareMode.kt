package com.raktufin.composehomerecipes.domain.models

typealias PrepareModeDomain = PrepareMode

data class PrepareMode(
    val id: Int = 0,
    val name: String,
    val recipeId: Int
)
