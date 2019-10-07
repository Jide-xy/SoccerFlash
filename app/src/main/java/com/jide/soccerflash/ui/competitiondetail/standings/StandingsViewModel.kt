package com.jide.soccerflash.ui.competitiondetail.standings

import androidx.lifecycle.*
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.models.TablePositionDetails
import com.jide.soccerflash.repository.Repository
import javax.inject.Inject

class StandingsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _standingsLiveData = MutableLiveData<Int>()
    val standingsLiveData: LiveData<Resource<List<TablePositionDetails>>> =
        _standingsLiveData.switchMap {
            liveData { emitSource(repository.getStandings(it)) }
        }

    fun getStandings(id: Int) {
        _standingsLiveData.value = id
    }
}