package com.jide.soccerflash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jide.soccerflash.models.TeamPlayer

@Dao
abstract class PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun savePlayers(players: List<TeamPlayer>)

    @Query("SELECT * FROM TeamPlayer WHERE teamId = :id")
    abstract fun getPlayersForTeam(id: Int): LiveData<List<TeamPlayer>>
}