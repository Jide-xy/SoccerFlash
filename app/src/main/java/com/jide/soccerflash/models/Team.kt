package com.jide.soccerflash.models

import androidx.room.*
import com.jide.soccerflash.room.ListConverter

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity
data class Team(
    @PrimaryKey val id: Int,
    var competitionId: Int?,
    @Embedded(prefix = "area") val area: Area?,
    val name: String?,
    val shortName: String?,
    val crestUrl: String?,
    val tla: String?,
    val address: String?,
    val phone: String?,
    val website: String?,
    val email: String?,
    val founded: Int?,
    val clubColors: String?,
    val venue: String?,
    @field:TypeConverters(ListConverter::class) val squad: List<TeamPlayer>?,
    val lastUpdated: String?
)

@Entity
data class TeamPlayer(
    @PrimaryKey val id: Int?,
    var teamId: Int?,
    val name: String?,
    val dateOfBirth: String?,
    val countryOfBirth: String?,
    val nationality: String?,
    val position: String?,
    val role: String?,
    val shirtNumber: Int?
)

data class TeamApiResult(
    val count: Int?, val teams: List<Team>, val competition: Competition?,
    val season: Season?
)