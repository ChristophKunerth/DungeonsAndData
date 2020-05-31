package com.example.dungeonsanddata.data.api

class ApiHelper(private val api: Api) {

    fun getPlayers() = api.getPlayers()
    fun getSpells() = api.getSpells()
}