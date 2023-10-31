package com.raktufin.composehomerecipes.data.repositories

import com.raktufin.composehomerecipes.data.datasources.RecipesDataSource
import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.domain.models.IngredientDomain
import com.raktufin.composehomerecipes.domain.models.PrepareModeDomain
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val localDataSource: RecipesDataSource.Local
) : RecipesRepository {
    override suspend fun getAllRecipes(): Flow<List<RecipeDomain>> = localDataSource.getAllRecipes()

    override suspend fun insert(recipe: RecipeDomain) = localDataSource.insert(recipe)

    override suspend fun getFullRecipe(recipeId: Int): Flow<FullRecipeDomain> =
        localDataSource.getFullRecipe(recipeId)

    override suspend fun insertIngredient(recipeId: Int, ingredientName: String) =
        localDataSource.insert(
            IngredientDomain(
                name = ingredientName,
                recipeId = recipeId
            )
        )

    override suspend fun insertPrepareMode(recipeId: Int, prepareModeName: String) =
        localDataSource.insert(PrepareModeDomain(
            name = prepareModeName,
            recipeId = recipeId
        ))
}