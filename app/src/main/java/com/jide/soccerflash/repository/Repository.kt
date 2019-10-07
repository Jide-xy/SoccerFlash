package com.jide.soccerflash.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.SFService
import com.jide.soccerflash.models.*
import com.jide.soccerflash.room.db.SFDatabase
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Repository @Inject constructor(val localDb: SFDatabase, val remoteService: SFService) {


    suspend fun getTodaysMatches(): LiveData<Resource<List<MatchDetails>>> {
//        return liveData(Dispatchers.IO) { emit(Resource.success(remoteService.getTodaysMatches().body())) }
        return object : NetworkBoundResource<List<MatchDetails>, MatchApiResult>() {
            override suspend fun saveCallResult(item: MatchApiResult) {
                localDb.matchesDao().clearTable()
                localDb.matchesDao().saveMatches(item.matches)
            }

            override fun shouldFetch(data: List<MatchDetails>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<MatchDetails>> {
                return localDb.matchesDao().getAllMatches()
            }

            override suspend fun createCall(): Response<MatchApiResult> {
                return remoteService.getTodaysMatches()
            }

        }.asLiveData()
    }

    suspend fun getMatchesForCompetition(id: Int): LiveData<Resource<List<MatchDetails>>> {
        return object : NetworkBoundResource<List<MatchDetails>, MatchApiResult>() {
            override suspend fun saveCallResult(item: MatchApiResult) {
                localDb.matchesDao().saveMatches(item.matches.map {
                    it.competition =
                        Competition(item.competition?.id, null, null, null, null, null, null, null)
                    it.forCompetition = true
                    return@map it
                })
            }

            override fun shouldFetch(data: List<MatchDetails>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<MatchDetails>> {
                return localDb.matchesDao().getAllMatchesForCompetition(id)
            }

            override suspend fun createCall(): Response<MatchApiResult> {
                return remoteService.getMatchesForCompetition(id)
            }

        }.asLiveData()
    }

    suspend fun getCompetitions(): LiveData<Resource<List<Competition>>> {
        return object : NetworkBoundResource<List<Competition>, CompetitionsApiResult>() {
            override suspend fun saveCallResult(item: CompetitionsApiResult) {
                localDb.competitionDao().saveCompetitions(item.competitions)
            }

            override fun shouldFetch(data: List<Competition>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<Competition>> {
                return localDb.competitionDao().getCompetitions()
            }

            override suspend fun createCall(): Response<CompetitionsApiResult> {
                return remoteService.getCompetitions()
            }

        }.asLiveData()
    }

    suspend fun getStandings(id: Int): LiveData<Resource<List<TablePositionDetails>>> {
        return object : NetworkBoundResource<List<TablePositionDetails>, StandingsApiResult>() {
            override suspend fun saveCallResult(item: StandingsApiResult) {
                localDb.standingDao().saveStandings(
                    item.standings?.get(0)?.table?.map {
                        it.competitionId = item.competition?.id
                        it.id = it.team?.id
                        return@map it
                    } ?: emptyList())
            }

            override fun shouldFetch(data: List<TablePositionDetails>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<TablePositionDetails>> {
                return localDb.standingDao().getStandingsForCompetition(id).map {
                    it.sortedBy { x ->
                        x.position
                    }
                }
            }

            override suspend fun createCall(): Response<StandingsApiResult> {
                return remoteService.getStandingsForCompetition(id)
            }

        }.asLiveData()

    }

    suspend fun getCompetitionTeams(id: Int): LiveData<Resource<List<Team>>> {
        return object : NetworkBoundResource<List<Team>, TeamApiResult>() {
            override suspend fun saveCallResult(item: TeamApiResult) {
                localDb.teamDao().saveTeams(
                    item.teams.map {
                        it.competitionId = item.competition?.id
                        return@map it
                    })
            }

            override fun shouldFetch(data: List<Team>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<Team>> {
                return localDb.teamDao().getTeamsForCompetition(id)
            }

            override suspend fun createCall(): Response<TeamApiResult> {
                return remoteService.getTeams(id)
            }

        }.asLiveData()
    }

    suspend fun getTeamPlayers(id: Int): LiveData<Resource<List<TeamPlayer>>> {
        return object : NetworkBoundResource<List<TeamPlayer>, Team>() {
            override suspend fun saveCallResult(item: Team) {
                localDb.playerDao().savePlayers(
                    item.squad?.map {
                        it.teamId = item.id
                        return@map it
                    } ?: emptyList()
                )
            }

            override fun shouldFetch(data: List<TeamPlayer>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): LiveData<List<TeamPlayer>> {
                return localDb.playerDao().getPlayersForTeam(id)
                    .map { it.sortedBy { x -> x.shirtNumber } }
            }

            override suspend fun createCall(): Response<Team> {
                return remoteService.getTeamSquad(id)
            }

        }.asLiveData()
    }

}