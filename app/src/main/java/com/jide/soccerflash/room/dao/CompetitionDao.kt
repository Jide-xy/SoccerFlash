package com.jide.soccerflash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jide.soccerflash.models.Competition

@Dao
abstract class CompetitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveCompetitions(competitions: List<Competition>)

    @Query("SELECT * FROM Competition")
    abstract fun getCompetitions(): LiveData<List<Competition>>
}