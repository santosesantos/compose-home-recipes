package com.raktufin.composehomerecipes.data.datasources

import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.domain.models.IngredientDomain
import com.raktufin.composehomerecipes.domain.models.PrepareModeDomain
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import kotlinx.coroutines.flow.Flow

sealed interface RecipesDataSource {
    interface Local: RecipesDataSource {
        suspend fun getAllRecipes(): Flow<List<RecipeDomain>>
        suspend fun insert(recipe: RecipeDomain)
        suspend fun getFullRecipe(recipeId: Int): Flow<FullRecipeDomain>
        suspend fun insert(ingredient: IngredientDomain)
        suspend fun insert(prepareMode: PrepareModeDomain)
    }
}