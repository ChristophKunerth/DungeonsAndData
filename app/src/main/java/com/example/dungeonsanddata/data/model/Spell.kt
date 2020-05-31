package com.example.dungeonsanddata.data.model

import com.google.gson.annotations.SerializedName

data class Spell(
    @SerializedName("index")
    val index : String = "",
    @SerializedName("name")
    val name : String = "",
    @SerializedName("url")
    val url : String = "",

    @SerializedName("duration")
    val duration : String = "",
    @SerializedName("concentration")
    val concentration : Boolean = false,
    @SerializedName("casting_time")
    val casting_time : String = "",
    @SerializedName("level")
    val level : Int = 0
)