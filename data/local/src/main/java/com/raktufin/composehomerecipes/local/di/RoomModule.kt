package com.raktufin.composehomerecipes.local.di

import android.content.Context
import androidx.room.Room
import com.raktufin.composehomerecipes.local.AppDatabase
import com.raktufin.composehomerecipes.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "recipes_database",
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }
}

