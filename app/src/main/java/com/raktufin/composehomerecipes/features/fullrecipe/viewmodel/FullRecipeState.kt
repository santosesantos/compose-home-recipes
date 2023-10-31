package com.raktufin.composehomerecipes.features.fullrecipe.viewmodel

import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain

sealed interface FullRecipeState {
    object Loading: FullRecipeState
    data class Error(val message: String): FullRecipeState
    data class Success(val fullRecipe: FullRecipeDomain): FullRecipeState
}