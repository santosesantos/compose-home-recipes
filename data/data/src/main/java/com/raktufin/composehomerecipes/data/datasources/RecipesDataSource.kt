package com.raktufin.composehomerecipes.data.datasources

import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import kotlinx.coroutines.flow.Flow

sealed interface RecipesDataSource {
    interface Local: RecipesDataSource {
        suspend fun getAllRecipes(): Flow<List<RecipeDomain>>
        suspend fun insert(recipe: RecipeDomain)
    }
}