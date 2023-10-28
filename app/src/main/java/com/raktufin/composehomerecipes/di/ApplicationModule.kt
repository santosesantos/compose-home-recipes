package com.raktufin.composehomerecipes.di

import android.app.Application
import android.content.Context
import com.raktufin.composehomerecipes.data.di.DataModule
import com.raktufin.composehomerecipes.local.di.LocalDataModule
import com.raktufin.composehomerecipes.local.di.RoomModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DataModule::class,
        LocalDataModule::class,
        RoomModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}