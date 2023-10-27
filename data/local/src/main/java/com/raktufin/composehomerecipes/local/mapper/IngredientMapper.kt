package com.raktufin.composehomerecipes.local.mapper

import com.raktufin.composehomerecipes.domain.models.IngredientDomain
import com.raktufin.composehomerecipes.local.entities.IngredientEntity

fun IngredientEntity.toDomain() = IngredientDomain(
    id = id,
    name = name,
    recipeId = recipeId
)

fun IngredientDomain.toEntity() = IngredientEntity(
    id = id,
    name = name,
    recipeId = recipeId
)