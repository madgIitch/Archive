package com.example.archive.ui.outfits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.archive.data.local.dao.ItemDao
import com.example.archive.data.local.dao.OutfitDao
import com.example.archive.data.local.entities.ItemEntity
import com.example.archive.data.local.entities.OutfitEntity
import com.example.archive.data.local.entities.OutfitItemCrossRef
import com.example.archive.data.local.entities.OutfitWithItems
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class OutfitsViewModel(
    private val outfitDao: OutfitDao,
    private val itemDao: ItemDao
) : ViewModel() {

    val outfits: StateFlow<List<OutfitWithItems>> = outfitDao.getAllOutfitsWithItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val availableItems: StateFlow<List<ItemEntity>> = itemDao.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createOutfit(name: String, selectedItemIds: List<String>) {
        viewModelScope.launch {
            val outfitId = UUID.randomUUID().toString()
            val outfit = OutfitEntity(
                id = outfitId,
                name = name,
                createdAt = System.currentTimeMillis()
            )
            outfitDao.insertOutfit(outfit)

            selectedItemIds.forEach { itemId ->
                outfitDao.insertOutfitItemCrossRef(
                    OutfitItemCrossRef(outfitId, itemId)
                )
            }
        }
    }

    fun deleteOutfit(outfit: OutfitEntity) {
        viewModelScope.launch {
            outfitDao.deleteOutfitItems(outfit.id)
            outfitDao.deleteOutfit(outfit)
        }
    }
}