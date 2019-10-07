package com.jide.soccerflash.ui.competitiondetail.teams

import androidx.lifecycle.*
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.models.Team
import com.jide.soccerflash.models.TeamPlayer
import com.jide.soccerflash.repository.Repository
import javax.inject.Inject

class TeamsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _teamsLiveData = MutableLiveData<Int>()
    val teamsLiveData: LiveData<Resource<List<Team>>> = _teamsLiveData.switchMap {
        liveData { emitSource(repository.getCompetitionTeams(it)) }
    }

    private val _teamPlayerLiveData = MutableLiveData<Int>()
    val teamPlayerLiveData: LiveData<Resource<List<TeamPlayer>>> = _teamPlayerLiveData.switchMap {
        liveData { emitSource(repository.getTeamPlayers(it)) }
    }

    fun getTeams(id: Int) {
        _teamsLiveData.value = id
    }

    fun getPlayers(id: Int) {
        _teamPlayerLiveData.value = id
    }
}
