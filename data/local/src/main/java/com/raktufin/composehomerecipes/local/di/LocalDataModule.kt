package com.raktufin.composehomerecipes.local.di

import com.raktufin.composehomerecipes.data.datasources.RecipesDataSource
import com.raktufin.composehomerecipes.local.AppDatabase
import com.raktufin.composehomerecipes.local.RecipesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {
    @Binds
    fun providesRecipesDataSourceLocal(impl: RecipesLocalDataSourceImpl): RecipesDataSource.Local
}
