package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(recipe: RecipeDomain) = repository.insert(recipe)
}