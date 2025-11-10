package com.example.archive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.data.local.dao.ItemDao
import com.example.app.data.local.dao.OutfitDao
import com.example.app.data.local.entities.ItemEntity
import com.example.app.data.local.entities.OutfitEntity
import com.example.app.data.local.entities.OutfitItemCrossRef
import com.example.app.data.local.entities.PlannerEntity
import com.example.archive.data.local.dao.ItemDao
import com.example.archive.data.local.entities.ItemEntity

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