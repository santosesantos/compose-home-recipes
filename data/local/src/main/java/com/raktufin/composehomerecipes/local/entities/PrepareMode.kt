package com.raktufin.composehomerecipes.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

typealias PrepareModeEntity = PrepareMode

@Entity
data class PrepareMode(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "recipeId") val recipeId: Int
)
