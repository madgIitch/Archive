package com.example.archive.data.repository

import com.example.archive.data.local.dao.ItemDao
import com.example.archive.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

class ItemRepository(
    private val itemDao: ItemDao
) {
    fun getAllItems(): Flow<List<ItemEntity>> = itemDao.getAllItems()

    suspend fun insertItem(item: ItemEntity) = itemDao.insertItem(item)

    suspend fun deleteItem(item: ItemEntity) = itemDao.deleteItem(item)
}