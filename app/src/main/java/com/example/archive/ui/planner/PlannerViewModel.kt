package com.example.archive.ui.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.archive.data.local.dao.OutfitDao
import com.example.archive.data.local.dao.PlannerDao
import com.example.archive.data.local.entities.OutfitEntity
import com.example.archive.data.local.entities.PlannerEntity
import com.example.archive.data.local.entities.PlannerWithOutfit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.Calendar

class PlannerViewModel(
    private val plannerDao: PlannerDao,
    private val outfitDao: OutfitDao
) : ViewModel() {

    val plannedOutfits: StateFlow<List<PlannerWithOutfit>> =
        plannerDao.getAllPlannedOutfitsWithDetails()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val availableOutfits: StateFlow<List<OutfitEntity>> =
        outfitDao.getAllOutfits()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun planOutfit(outfitId: String, date: Long, notes: String?) {
        viewModelScope.launch {
            val planner = PlannerEntity(
                id = UUID.randomUUID().toString(),
                outfitId = outfitId,
                date = date,
                notes = notes,
                createdAt = System.currentTimeMillis()
            )
            plannerDao.insertPlannedOutfit(planner)
        }
    }

    fun deletePlannedOutfit(planner: PlannerEntity) {
        viewModelScope.launch {
            plannerDao.deletePlannedOutfit(planner)
        }
    }

    fun clearPastPlannedOutfits() {
        viewModelScope.launch {
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            plannerDao.deletePastPlannedOutfits(today)
        }
    }
}