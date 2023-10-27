package com.raktufin.composehomerecipes.local.di

import com.raktufin.composehomerecipes.data.datasources.RecipesDataSource
import com.raktufin.composehomerecipes.local.RecipesLocalDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface LocalDataModule {
    @Binds
    fun providesRecipesDataSourceLocal(impl: RecipesLocalDataSourceImpl): RecipesDataSource.Local
}