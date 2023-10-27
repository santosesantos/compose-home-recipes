package com.raktufin.composehomerecipes.local

import android.content.Context
import androidx.room.Room

val Context.db: AppDatabase
    get() = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "database-recipe"
    ).build()