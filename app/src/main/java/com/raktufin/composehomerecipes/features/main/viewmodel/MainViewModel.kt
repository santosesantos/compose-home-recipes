package com.raktufin.composehomerecipes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.domain.usecases.GetAllRecipesUseCase
import com.raktufin.composehomerecipes.domain.usecases.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
): ViewModel() {
    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() = viewModelScope.launch {
        getAllRecipesUseCase()
            .flowOn(Dispatchers.Main)
            .onStart {
                _state.value = MainUiState.Loading
            }.catch {
                _state.value = MainUiState.Error(it.message.toString())
            }.collect { recipes ->
                if (recipes.isEmpty())
                    _state.value = MainUiState.Empty
                else
                    _state.value = MainUiState.Success(recipes)
            }
    }

    fun insertRecipe(name: String) = viewModelScope.launch {
        insertRecipeUseCase(RecipeDomain(name = name))
    }
}