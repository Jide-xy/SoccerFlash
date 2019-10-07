package com.jide.soccerflash.api

import com.jide.soccerflash.BuildConfig
import com.jide.soccerflash.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface SFService {

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("matches/")
    suspend fun getTodaysMatches(): Response<MatchApiResult>

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("competitions/")
    suspend fun getCompetitions(): Response<CompetitionsApiResult>

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("competitions/{id}/teams")
    suspend fun getTeams(@Path("id") id: Int): Response<TeamApiResult>

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("competitions/{id}/standings")
    suspend fun getStandingsForCompetition(@Path("id") id: Int): Response<StandingsApiResult>

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("teams/{id}")
    suspend fun getTeamSquad(@Path("id") id: Int): Response<Team>

    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    @GET("competitions/{id}/matches")
    suspend fun getMatchesForCompetition(@Path("id") id: Int): Response<MatchApiResult>
}