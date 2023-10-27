package com.raktufin.composehomerecipes.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.raktufin.composehomerecipes.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)
}