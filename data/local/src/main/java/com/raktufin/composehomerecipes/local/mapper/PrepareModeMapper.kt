package com.raktufin.composehomerecipes.local.mapper

import com.raktufin.composehomerecipes.domain.models.PrepareModeDomain
import com.raktufin.composehomerecipes.local.entities.PrepareModeEntity

fun PrepareModeEntity.toDomain() = PrepareModeDomain(
    id = id,
    name = name,
    recipeId = recipeId
)

fun PrepareModeDomain.toEntity() = PrepareModeEntity(
    id = id,
    name = name,
    recipeId = recipeId
)