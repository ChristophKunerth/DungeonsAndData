package com.example.dungeonsanddata.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dungeonsanddata.data.model.Player
import com.example.dungeonsanddata.data.repository.MainRepository
import com.example.dungeonsanddata.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val players = MutableLiveData<Resource<List<Player>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        players.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    players.postValue(Resource.success(userList))
                }, {
                    players.postValue(Resource.error(null, "MainViewModel Error"))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getPlayers(): LiveData<Resource<List<Player>>> {
        return players
    }
}