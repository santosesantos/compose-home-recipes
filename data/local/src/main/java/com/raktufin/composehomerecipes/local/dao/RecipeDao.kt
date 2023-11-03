package com.raktufin.composehomerecipes.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Update
    fun update(ingredient: IngredientEntity)

    @Update
    fun update(prepareMode: PrepareModeEntity)

    @Delete
    fun delete(ingredient: IngredientEntity)

    @Delete
    fun delete(prepareMode: PrepareModeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)
}