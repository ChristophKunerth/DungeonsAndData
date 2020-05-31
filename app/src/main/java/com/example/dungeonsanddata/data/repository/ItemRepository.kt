package com.example.dungeonsanddata.data.repository

import androidx.lifecycle.LiveData
import com.example.dungeonsanddata.data.model.Item
import com.example.dungeonsanddata.data.room.ItemDAO

class ItemRepository(private val itemDAO: ItemDAO) {

    val allItems: LiveData<List<Item>> = itemDAO.getAllItems()

    suspend fun insert(item: Item) =
        itemDAO.insert(item)
}