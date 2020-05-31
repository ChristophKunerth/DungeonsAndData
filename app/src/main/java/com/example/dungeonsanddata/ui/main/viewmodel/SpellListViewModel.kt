package com.example.dungeonsanddata.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dungeonsanddata.data.model.Spell
import com.example.dungeonsanddata.data.repository.SpellListRepository
import com.example.dungeonsanddata.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SpellListViewModel(private val spellListRepository: SpellListRepository) : ViewModel() {

    private val spells = MutableLiveData<Resource<List<Spell>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchSpells()
    }

    private fun fetchSpells() {
        spells.postValue(Resource.loading(null))
        compositeDisposable.add(
            spellListRepository.getSpells()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ spellList ->
                    spells.postValue(Resource.success(spellList))
                }, {
                    spells.postValue(Resource.error(null, "SpellListViewModel Error fetchSpells"))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getSpells(): LiveData<Resource<List<Spell>>> {
        return spells
    }
}
