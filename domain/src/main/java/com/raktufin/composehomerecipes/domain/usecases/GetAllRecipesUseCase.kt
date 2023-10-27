package com.raktufin.composehomerecipes.domain.usecases

import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke() = repository.getAllRecipes()
}