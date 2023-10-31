package com.raktufin.composehomerecipes.local

import com.raktufin.composehomerecipes.data.datasources.RecipesDataSource
import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.domain.models.IngredientDomain
import com.raktufin.composehomerecipes.domain.models.PrepareModeDomain
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.local.dao.RecipeDao
import com.raktufin.composehomerecipes.local.mapper.toDomain
import com.raktufin.composehomerecipes.local.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipesLocalDataSourceImpl @Inject constructor(
    private val dao: RecipeDao
): RecipesDataSource.Local {
    override suspend fun getAllRecipes(): Flow<List<RecipeDomain>> = withContext(Dispatchers.IO) {
        dao.getAllRecipes().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insert(recipe: RecipeDomain) = withContext(Dispatchers.IO) {
        dao.insert(recipe.toEntity())
    }

    override suspend fun getFullRecipe(recipeId: Int): Flow<FullRecipeDomain> = withContext(Dispatchers.IO) {
        dao.getFullRecipe(recipeId).map { it.toDomain() }
    }

    override suspend fun insert(ingredient: IngredientDomain) = withContext(Dispatchers.IO) {
        dao.insert(ingredient.toEntity())
    }

    override suspend fun insert(prepareMode: PrepareModeDomain) = withContext(Dispatchers.IO) {
        dao.insert(prepareMode.toEntity())
    }
}