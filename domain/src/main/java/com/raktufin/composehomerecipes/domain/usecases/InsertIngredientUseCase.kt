package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class InsertIngredientUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(recipeId: Int, ingredientName: String) =
        repository.insertIngredient(recipeId, ingredientName)
}