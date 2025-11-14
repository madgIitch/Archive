package com.example.archive.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlannerWithOutfit(
    @Embedded val planner: PlannerEntity,
    @Relation(
        parentColumn = "outfitId",
        entityColumn = "id"
    )
    val outfit: OutfitEntity
)