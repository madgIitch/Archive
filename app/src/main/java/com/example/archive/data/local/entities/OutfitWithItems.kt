package com.example.archive.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OutfitWithItems(
    @Embedded val outfit: OutfitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = OutfitItemCrossRef::class,
            parentColumn = "outfitId",
            entityColumn = "itemId"
        )
    )
    val items: List<ItemEntity>
)