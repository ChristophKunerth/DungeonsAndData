package com.example.dungeonsanddata.data.repository

import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.model.Player
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getPlayers(): Single<List<Player>> {
        return apiHelper.getPlayers()
    }

}