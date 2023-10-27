package com.raktufin.composehomerecipes.data.di

import com.raktufin.composehomerecipes.data.repositories.RecipesRepositoryImpl
import com.raktufin.composehomerecipes.domain.repositories.RecipesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun provideRecipesRepository(impl: RecipesRepositoryImpl): RecipesRepository
}