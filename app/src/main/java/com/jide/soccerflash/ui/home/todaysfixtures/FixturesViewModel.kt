package com.jide.soccerflash.ui.home.todaysfixtures

import androidx.lifecycle.*
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.models.MatchDetails
import com.jide.soccerflash.repository.Repository
import javax.inject.Inject

class FixturesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _todaysMatchesLiveData = MutableLiveData<Int>()
    val todaysMatchesLiveData: LiveData<Resource<List<MatchDetails>>> =
        _todaysMatchesLiveData.switchMap {
            liveData { emitSource(repository.getTodaysMatches()) }
        }

    private val _matchesLiveData = MutableLiveData<Int>()
    val matchesLiveData: LiveData<Resource<List<MatchDetails>>> = _matchesLiveData.switchMap {
        liveData { emitSource(repository.getMatchesForCompetition(it)) }
    }

//    init {
//        _todaysMatchesLiveData.value = 1
//    }

    fun getTodaysMatches() {
        _todaysMatchesLiveData.value = 2
    }

    fun getMatchesForCompetition(id: Int) {
        _matchesLiveData.value = id
    }
}