package com.jide.soccerflash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jide.soccerflash.models.MatchDetails

@Dao
abstract class MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMatches(matchDetails: List<MatchDetails>)

    @Query("SELECT * FROM MatchDetails WHERE NOT forCompetition")
    abstract fun getAllMatches(): LiveData<List<MatchDetails>>

    @Query("SELECT * FROM MatchDetails WHERE competitionid = :id AND forCompetition")
    abstract fun getAllMatchesForCompetition(id: Int): LiveData<List<MatchDetails>>

    @Query("DELETE FROM MatchDetails WHERE NOT forCompetition")
    abstract fun clearTable()
}