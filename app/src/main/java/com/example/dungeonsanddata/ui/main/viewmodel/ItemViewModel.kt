package com.example.dungeonsanddata.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dungeonsanddata.data.model.Item
import com.example.dungeonsanddata.data.repository.ItemRepository
import com.example.dungeonsanddata.data.room.DungeonsAndDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

// AndroidViewModel has context in comparison to regular ViewModel
@InternalCoroutinesApi
class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository
    val allItems: LiveData<List<Item>>

    init {
        val itemDAO = DungeonsAndDatabase.getDatabase(application, viewModelScope).itemDAO()
        repository = ItemRepository(itemDAO)
        allItems = repository.allItems
    }

    // Function via co-routine to insert in a non-blocking manner
    fun insert(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
}