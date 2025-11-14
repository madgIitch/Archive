package com.example.archive.ui.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.archive.data.local.dao.ItemDao
import com.example.archive.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class WardrobeViewModel(
    private val itemDao: ItemDao
) : ViewModel() {

    val items: StateFlow<List<ItemEntity>> = itemDao.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addItem(name: String, category: String, imageUri: String?) {
        viewModelScope.launch {
            val item = ItemEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                category = category,
                imageUri = imageUri,
                createdAt = System.currentTimeMillis()
            )
            itemDao.insertItem(item)
        }
    }

    fun deleteItem(item: ItemEntity) {
        viewModelScope.launch {
            itemDao.deleteItem(item)
        }
    }
}