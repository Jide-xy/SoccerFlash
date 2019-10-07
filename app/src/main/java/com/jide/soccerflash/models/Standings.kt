package com.jide.soccerflash.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class StandingsApiResult(
    val competition: Competition?,
    val season: Season?,
    val standings: List<Standing>?
)

data class Standing(
    val type: String?,
    val group: String?,
    val stage: String?,
    val table: List<TablePositionDetails>?
)

@Entity
data class TablePositionDetails(
    @PrimaryKey var id: Int?,
    var competitionId: Int?,
    val position: Int?,
    @Embedded(prefix = "team") val team: StandingsTeam?,
    val playedGames: Int?,
    val won: Int?,
    val draw: Int?,
    val lost: Int?,
    val points: Int?,
    val goalsFor: Int?,
    val goalsAgainst: Int?,
    val goalDifference: Int?
)

data class StandingsTeam(
    val id: Int?,
    val name: String?,
    val crestUrl: String?
)