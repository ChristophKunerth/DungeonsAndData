package com.example.dungeonsanddata.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.repository.*
import com.example.dungeonsanddata.ui.main.viewmodel.*
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    // TODO: Add the ItemViewModel here aswell
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(SpellListViewModel::class.java)) {
            return SpellListViewModel(SpellListRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Class not applicable")
    }

}