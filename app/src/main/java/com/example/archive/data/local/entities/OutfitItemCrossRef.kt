package com.example.archive.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "outfit_item_cross_ref",
    primaryKeys = ["outfitId", "itemId"]
)
data class OutfitItemCrossRef(
    val outfitId: String,
    val itemId: String
)