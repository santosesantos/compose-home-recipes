package com.raktufin.composehomerecipes.local.mapper

import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.local.entities.FullRecipeEntity

fun FullRecipeEntity.toDomain() = FullRecipeDomain(
    recipe = recipe.toDomain(),
    ingredients = ingredients.map { it.toDomain() },
    prepareModes = prepareModes.map { it.toDomain() }
)

fun FullRecipeDomain.toEntity() = FullRecipeEntity(
    recipe = recipe.toEntity(),
    ingredients = ingredients.map { it.toEntity() },
    prepareModes = prepareModes.map { it.toEntity() }
)