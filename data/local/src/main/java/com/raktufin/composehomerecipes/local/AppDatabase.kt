package com.raktufin.composehomerecipes.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raktufin.composehomerecipes.local.dao.RecipeDao
import com.raktufin.composehomerecipes.local.entities.IngredientEntity
import com.raktufin.composehomerecipes.local.entities.PrepareModeEntity
import com.raktufin.composehomerecipes.local.entities.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        PrepareModeEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}