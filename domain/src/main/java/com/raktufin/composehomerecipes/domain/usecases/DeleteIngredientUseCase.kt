package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class DeleteIngredientUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, recipeId: Int) =
        repository.deleteIngredient(ingredientId, recipeId)
}