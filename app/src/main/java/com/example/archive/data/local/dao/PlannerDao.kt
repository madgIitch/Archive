package com.example.archive.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.archive.data.local.entities.PlannerEntity
import com.example.archive.data.local.entities.PlannerWithOutfit

@Dao
interface PlannerDao {
    @Query("SELECT * FROM planner ORDER BY date ASC")
    fun getAllPlannedOutfits(): Flow<List<PlannerEntity>>

    @Transaction
    @Query("SELECT * FROM planner ORDER BY date ASC")
    fun getAllPlannedOutfitsWithDetails(): Flow<List<PlannerWithOutfit>>

    @Transaction
    @Query("SELECT * FROM planner WHERE date >= :startDate AND date < :endDate ORDER BY date ASC")
    fun getPlannedOutfitsForDateRange(startDate: Long, endDate: Long): Flow<List<PlannerWithOutfit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlannedOutfit(planner: PlannerEntity)

    @Delete
    suspend fun deletePlannedOutfit(planner: PlannerEntity)

    @Query("DELETE FROM planner WHERE date < :date")
    suspend fun deletePastPlannedOutfits(date: Long)
}