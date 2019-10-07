package com.jide.soccerflash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jide.soccerflash.models.TablePositionDetails

@Dao
abstract class StandingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveStandings(standings: List<TablePositionDetails>)

    @Query("SELECT * FROM TablePositionDetails WHERE competitionId = :id")
    abstract fun getStandingsForCompetition(id: Int): LiveData<List<TablePositionDetails>>
}