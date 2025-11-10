package com.example.archive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.archive.data.local.dao.OutfitDao
import com.example.archive.data.local.entities.ItemEntity
import com.example.archive.data.local.entities.OutfitEntity
import com.example.archive.data.local.entities.OutfitItemCrossRef
import com.example.archive.data.local.entities.PlannerEntity
import com.example.archive.data.local.dao.ItemDao

@Database(
    entities = [
        ItemEntity::class,
        OutfitEntity::class,
        OutfitItemCrossRef::class,
        PlannerEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun outfitDao(): OutfitDao
}