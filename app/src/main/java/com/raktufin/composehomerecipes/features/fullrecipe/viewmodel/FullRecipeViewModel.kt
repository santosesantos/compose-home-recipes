package com.raktufin.composehomerecipes.features.fullrecipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raktufin.composehomerecipes.domain.usecases.DeleteIngredientUseCase
import com.raktufin.composehomerecipes.domain.usecases.DeletePrepareModeUseCase
import com.raktufin.composehomerecipes.domain.usecases.GetFullRecipeUseCase
import com.raktufin.composehomerecipes.domain.usecases.InsertIngredientUseCase
import com.raktufin.composehomerecipes.domain.usecases.InsertPrepareModeUseCase
import com.raktufin.composehomerecipes.domain.usecases.UpdateIngredientUseCase
import com.raktufin.composehomerecipes.domain.usecases.UpdatePrepareModeUseCase
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
    private val insertPrepareModeUseCase: InsertPrepareModeUseCase,
    private val updateIngredientUseCase: UpdateIngredientUseCase,
    private val updatePrepareModeUseCase: UpdatePrepareModeUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase,
    private val deletePrepareModeUseCase: DeletePrepareModeUseCase
): ViewModel() {
    private val _state = MutableStateFlow<FullRecipeState>(FullRecipeState.Loading)
    val state: StateFlow<FullRecipeState> = _state

    private var currentRecipeId: Int = 0

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

    fun updateIngredient(ingredientId: Int, ingredientName: String) = viewModelScope.launch {
        updateIngredientUseCase(ingredientId, ingredientName, currentRecipeId)
    }

    fun updatePrepareMode(prepareModeId: Int, prepareModeName: String) = viewModelScope.launch {
        updatePrepareModeUseCase(prepareModeId, prepareModeName, currentRecipeId)
    }

    fun deleteIngredient(ingredientId: Int) = viewModelScope.launch {
        deleteIngredientUseCase(ingredientId, currentRecipeId)
    }

    fun deletePrepareMode(prepareModeId : Int) = viewModelScope.launch {
        deletePrepareModeUseCase(prepareModeId, currentRecipeId)
    }
}