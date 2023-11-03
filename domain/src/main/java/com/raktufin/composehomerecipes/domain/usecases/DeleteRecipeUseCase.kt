package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(recipeId: Int) = repository.deleteRecipe(recipeId)
}