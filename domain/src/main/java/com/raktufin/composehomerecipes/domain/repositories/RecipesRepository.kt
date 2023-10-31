package com.raktufin.composehomerecipes.domain.repositories

import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun getAllRecipes(): Flow<List<RecipeDomain>>
    suspend fun insert(recipe: RecipeDomain)
    suspend fun getFullRecipe(recipeId: Int): Flow<FullRecipeDomain>
    suspend fun insertIngredient(recipeId: Int, ingredientName: String)
    suspend fun insertPrepareMode(recipeId: Int, prepareModeName: String)
}