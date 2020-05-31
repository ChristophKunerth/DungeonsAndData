package com.example.dungeonsanddata.data.api

import com.example.dungeonsanddata.data.model.Player
import com.example.dungeonsanddata.data.model.Spell
import io.reactivex.Single

interface Api {
    fun getPlayers(): Single<List<Player>>
    fun getSpells(): Single<List<Spell>>
}