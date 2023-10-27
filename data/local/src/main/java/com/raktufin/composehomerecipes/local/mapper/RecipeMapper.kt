package com.raktufin.composehomerecipes.local.mapper

import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.local.entities.RecipeEntity

fun RecipeEntity.toDomain() = RecipeDomain(
    id = id,
    name = name
)

fun RecipeDomain.toEntity() = RecipeEntity(
    id = id,
    name = name
)