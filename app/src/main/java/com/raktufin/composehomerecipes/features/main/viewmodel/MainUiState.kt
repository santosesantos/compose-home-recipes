package com.raktufin.composehomerecipes.features.main.viewmodel

import com.raktufin.composehomerecipes.domain.models.RecipeDomain

sealed interface MainUiState {
    object Loading: MainUiState
    object Empty: MainUiState
    data class Success(val recipes: List<RecipeDomain>): MainUiState
    data class Error(val message: String): MainUiState
}