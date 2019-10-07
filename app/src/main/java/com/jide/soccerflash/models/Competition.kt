package com.jide.soccerflash.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.jide.soccerflash.room.ListConverter

data class CompetitionsApiResult(
    val count: Int,
    val competitions: List<Competition>
)

@Entity
data class Competition(
    @PrimaryKey var id: Int?,
    @Embedded(prefix = "area") val area: Area?,
    val name: String?,
    val code: String?,
    val plan: String?,
    @Embedded(prefix = "currentSeason") val currentSeason: Season?,
    @field:TypeConverters(ListConverter::class) val seasons: List<Season>?,
    val lastUpdated: String?
)

data class Area(
    val id: Int?,
    val name: String?

)

data class Season(
    val id: Int?,
    val startDate: String?,
    val endDate: String?,
    @field:SerializedName("currentMatchday")
    val currentMatchDay: Int?,
    @field:TypeConverters(ListConverter::class) val availableStages: List<String>?
)