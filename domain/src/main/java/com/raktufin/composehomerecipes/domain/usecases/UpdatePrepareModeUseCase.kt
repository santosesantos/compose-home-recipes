package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class UpdatePrepareModeUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(prepareModeId: Int, prepareModeName: String, recipeId: Int) =
        repository.updateIngredient(prepareModeId, prepareModeName, recipeId)
}