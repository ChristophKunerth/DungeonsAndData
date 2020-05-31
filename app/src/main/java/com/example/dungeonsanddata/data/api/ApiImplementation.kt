package com.example.dungeonsanddata.data.api

import com.example.dungeonsanddata.data.model.Player
import com.example.dungeonsanddata.data.model.Spell
import io.reactivex.Single
import com.rx2androidnetworking.Rx2AndroidNetworking
import java.lang.StringBuilder

// Connects to our API (Check if the Website even keeps it alive)
class ApiImplementation : Api {

    override fun getPlayers(): Single<List<Player>> {
        return Rx2AndroidNetworking.get("https://5ec5115c2a4ba000163d1a47.mockapi.io/Players").build().getObjectListSingle(Player::class.java)
    }

    override fun getSpells(): Single<List<Spell>> {
        return Rx2AndroidNetworking.get("https://5ec5115c2a4ba000163d1a47.mockapi.io/Spells").build().getObjectListSingle(Spell::class.java)
    }
}