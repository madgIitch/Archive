package com.example.archive.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planner")
data class PlannerEntity(
    @PrimaryKey val id: String,
    val outfitId: String,
    val date: Long,  // Timestamp de la fecha planificada
    val notes: String?,
    val createdAt: Long
)