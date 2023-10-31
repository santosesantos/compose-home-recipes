package com.raktufin.composehomerecipes.features.fullrecipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raktufin.composehomerecipes.domain.usecases.GetFullRecipeUseCase
import com.raktufin.composehomerecipes.domain.usecases.InsertIngredientUseCase
import com.raktufin.composehomerecipes.domain.usecases.InsertPrepareModeUseCase
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
class FullRecipeViewModel @Inject constructor(
    private val getFullRecipeUseCase: GetFullRecipeUseCase,
    private val insertIngredientUseCase: InsertIngredientUseCase,
    private val insertPrepareModeUseCase: InsertPrepareModeUseCase
): ViewModel() {
    private val _state = MutableStateFlow<FullRecipeState>(FullRecipeState.Loading)
    val state: StateFlow<FullRecipeState> = _state

    private var currentRecipeId: Int = 0

    init {

    }

    fun getFullRecipe(recipeId: Int) = viewModelScope.launch {
        getFullRecipeUseCase(recipeId)
            .flowOn(Dispatchers.Main)
            .onStart {
                _state.value = FullRecipeState.Loading
            }.catch {
                _state.value = FullRecipeState.Error(it.message.toString())
            }.collect { fullRecipe ->
                currentRecipeId = recipeId
                _state.value = FullRecipeState.Success(fullRecipe)
            }
    }

    fun insertIngredient(ingredientName: String) = viewModelScope.launch {
        insertIngredientUseCase(currentRecipeId, ingredientName)
    }

    fun insertPrepareMode(prepareModeName: String) = viewModelScope.launch {
        insertPrepareModeUseCase(currentRecipeId, prepareModeName)
    }
}