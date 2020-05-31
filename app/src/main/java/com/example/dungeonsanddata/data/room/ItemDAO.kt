package com.example.dungeonsanddata.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dungeonsanddata.data.model.Item

@Dao
interface ItemDAO {

    @Query ("SELECT * from item_table ORDER BY item_name ASC")
    fun getAllItems(): LiveData<List<Item>>

    // OnConflict = Ignore Item Insert if it already exists
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query ("DELETE FROM item_table")
    suspend fun deleteAllItems()
}