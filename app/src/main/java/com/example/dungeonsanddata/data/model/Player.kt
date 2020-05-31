package com.example.dungeonsanddata.data.model

import com.google.gson.annotations.SerializedName

data class Player (
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)