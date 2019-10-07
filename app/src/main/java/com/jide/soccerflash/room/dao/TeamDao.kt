package com.jide.soccerflash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jide.soccerflash.models.Team

@Dao
abstract class TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTeams(teams: List<Team>)

    @Query("SELECT * FROM Team WHERE competitionId = :id")
    abstract fun getTeamsForCompetition(id: Int): LiveData<List<Team>>
}