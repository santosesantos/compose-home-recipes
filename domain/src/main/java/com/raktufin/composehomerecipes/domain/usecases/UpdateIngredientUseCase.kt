package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class UpdateIngredientUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, ingredientName: String, recipeId: Int) =
        repository.updateIngredient(ingredientId, ingredientName, recipeId)
}