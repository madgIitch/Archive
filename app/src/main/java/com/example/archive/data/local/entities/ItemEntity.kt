package com.example.archive.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val imageUri: String?,
    val createdAt: Long
)