package com.jide.soccerflash.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jide.soccerflash.models.*
import com.jide.soccerflash.room.dao.*

/**
 * Created by Babajide Mustapha on 9/18/2017.
 */
@Database(
    entities = [MatchDetails::class, Competition::class, TablePositionDetails::class, Team::class, TeamPlayer::class],
    version = 7
)
abstract class SFDatabase : RoomDatabase() {

    abstract fun matchesDao(): MatchDao
    abstract fun competitionDao(): CompetitionDao
    abstract fun teamDao(): TeamDao
    abstract fun standingDao(): StandingDao
    abstract fun playerDao(): PlayerDao
}
