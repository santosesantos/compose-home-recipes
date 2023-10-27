package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository

class InsertRecipeUseCase(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(recipe: RecipeDomain) = repository.insert(recipe)
}