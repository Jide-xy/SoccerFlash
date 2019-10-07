package com.jide.soccerflash.ui.home.competitions

import androidx.lifecycle.*
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.models.Competition
import com.jide.soccerflash.repository.Repository
import javax.inject.Inject

class CompetitionViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _competitionsLiveData = MutableLiveData<Boolean>()
    val competitionsLiveData: LiveData<Resource<List<Competition>>> =
        _competitionsLiveData.switchMap {
            liveData { emitSource(repository.getCompetitions()) }
        }

    init {
        _competitionsLiveData.value = true
    }

    fun refreshCompetitions() {
        _competitionsLiveData.value = true
    }

}