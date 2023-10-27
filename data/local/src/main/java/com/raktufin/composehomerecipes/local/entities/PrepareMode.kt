package com.raktufin.composehomerecipes.local.entities

typealias PrepareModeEntity = PrepareMode

data class PrepareMode(
    val id: Int = 0,
    val name: String,
    val recipeId: Int
)
