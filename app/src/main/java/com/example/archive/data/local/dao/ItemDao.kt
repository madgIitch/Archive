package com.example.archive.data.local.dao


import androidx.room.*
import com.arche.app.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)
}