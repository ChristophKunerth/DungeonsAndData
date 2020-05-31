package com.example.dungeonsanddata.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item (

    @PrimaryKey
    @ColumnInfo(name = "item_name")
    val name: String,

    @ColumnInfo(name = "item_value")
    val value: String,

    @ColumnInfo(name = "item_description")
    val description: String
)