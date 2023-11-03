package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class DeletePrepareModeUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(prepareModeId: Int, recipeId: Int) =
        repository.deletePrepareMode(prepareModeId, recipeId)
}