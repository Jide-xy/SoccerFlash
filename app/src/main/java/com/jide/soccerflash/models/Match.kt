package com.jide.soccerflash.models

import androidx.room.*
import com.jide.soccerflash.room.ListConverter

data class MatchApiResult(
    val count: Int,
    val matches: List<MatchDetails>,
    val competition: Competition?
)

data class Match(
    @Embedded(prefix = "head2head") val head2head: HeadToHead,
    @Embedded(prefix = "match") val match: MatchDetails?
)

data class HeadToHead(
    val numberOfMatches: Int?,
    val totalGoals: Int?,
    @Embedded(prefix = "competition") val homeTeam: TeamStats?,
    @Embedded(prefix = "competition") val awayTeam: TeamStats?
)

data class TeamStats(
    val wins: Int?,
    val draws: Int?,
    val losses: Int?
)

data class MatchPerson(
    val id: Int?,
    val name: String?,
    val countryOfBirth: String?,
    val nationality: String?,
    val position: String?,
    val shirtNumber: String?
)

data class MatchTeam(
    val id: Int?,
    val name: String?,
    @Embedded(prefix = "coach") val coach: MatchPerson?,
    @Embedded(prefix = "captain") val captain: MatchPerson?,
    @field:TypeConverters(ListConverter::class) val lineup: List<MatchPerson>?,
    @field:TypeConverters(ListConverter::class) val bench: List<MatchPerson>?
)

data class Score(
    val winner: String?,
    val duration: String?,
    @Embedded(prefix = "fullTime") val fullTime: ScorePair?,
    @Embedded(prefix = "halfTime") val halfTime: ScorePair?,
    @Embedded(prefix = "extraTime") val extraTime: ScorePair?,
    @Embedded(prefix = "penalties") val penalties: ScorePair?
)

data class ScorePair(val homeTeam: Int?, val awayTeam: Int?)

data class Substitution(
    val minute: Int?,
    @Embedded(prefix = "team") val team: MatchTeam?,
    @Embedded(prefix = "playerOut") val playerOut: MatchPerson?,
    @Embedded(prefix = "playerIn") val playerIn: MatchPerson?
)

data class Booking(
    val minute: Int?,
    @Embedded(prefix = "team") val team: MatchTeam?,
    @Embedded(prefix = "player") val player: MatchPerson?,
    val card: String?
)

data class Goal(
    val minute: Int?,
    val extraTime: String?,
    val type: String?,
    @Embedded(prefix = "team") val team: MatchTeam?,
    @Embedded(prefix = "scorer") val scorer: MatchPerson?,
    @Embedded(prefix = "assist") val assist: MatchPerson?
)

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity
data class MatchDetails(
    @PrimaryKey val id: Int,
    @Embedded(prefix = "competition") var competition: Competition?,
    @Embedded(prefix = "season") val season: Season?,
    val utcDate: String?,
    val status: String?,
    val minute: String?,
    val attendance: Int?,
    val venue: String?,
    val matchday: Int?,
    val stage: String?,
    val group: String?,
    val lastUpdated: String?,
    var forCompetition: Boolean,
    @Embedded(prefix = "homeTeam") val homeTeam: MatchTeam?,
    @Embedded(prefix = "awayTeam") val awayTeam: MatchTeam?,
    @Embedded(prefix = "score") val score: Score?,
    @field:TypeConverters(ListConverter::class) val goals: List<Goal>?,
    @field:TypeConverters(ListConverter::class) val bookings: List<Booking>?,
    @field:TypeConverters(ListConverter::class) val substitutions: List<Substitution>?,
    @field:TypeConverters(ListConverter::class) val referees: List<MatchPerson>?
)