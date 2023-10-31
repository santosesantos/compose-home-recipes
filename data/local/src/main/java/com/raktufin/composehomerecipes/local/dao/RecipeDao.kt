package com.raktufin.composehomerecipes.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.raktufin.composehomerecipes.local.entities.FullRecipeEntity
import com.raktufin.composehomerecipes.local.entities.IngredientEntity
import com.raktufin.composehomerecipes.local.entities.PrepareModeEntity
import com.raktufin.composehomerecipes.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    fun getFullRecipe(recipeId: Int): Flow<FullRecipeEntity>

    @Insert
    fun insert(ingredient: IngredientEntity)

    @Insert
    fun insert(prepareMode: PrepareModeEntity)
}