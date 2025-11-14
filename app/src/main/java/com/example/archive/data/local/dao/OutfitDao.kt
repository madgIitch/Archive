package com.example.archive.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.archive.data.local.entities.OutfitEntity
import com.example.archive.data.local.entities.OutfitItemCrossRef
import com.example.archive.data.local.entities.OutfitWithItems

@Dao
interface OutfitDao {
    @Query("SELECT * FROM outfits")
    fun getAllOutfits(): Flow<List<OutfitEntity>>

    @Transaction
    @Query("SELECT * FROM outfits")
    fun getAllOutfitsWithItems(): Flow<List<OutfitWithItems>>

    @Transaction
    @Query("SELECT * FROM outfits WHERE id = :outfitId")
    fun getOutfitWithItems(outfitId: String): Flow<OutfitWithItems?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfit(outfit: OutfitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfitItemCrossRef(crossRef: OutfitItemCrossRef)

    @Delete
    suspend fun deleteOutfit(outfit: OutfitEntity)

    @Query("DELETE FROM outfit_item_cross_ref WHERE outfitId = :outfitId")
    suspend fun deleteOutfitItems(outfitId: String)
}