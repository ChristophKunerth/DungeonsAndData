package com.example.dungeonsanddata.data.repository

import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.model.Spell
import io.reactivex.Single

class SpellListRepository(private val apiHelper: ApiHelper) {

    fun getSpells(): Single<List<Spell>> {
        return apiHelper.getSpells()
    }

}