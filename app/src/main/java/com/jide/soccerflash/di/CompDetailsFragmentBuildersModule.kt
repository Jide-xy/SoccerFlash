package com.jide.soccerflash.di

import com.jide.soccerflash.ui.competitiondetail.standings.StandingsFragment
import com.jide.soccerflash.ui.competitiondetail.teams.PlayersBottomSheet
import com.jide.soccerflash.ui.competitiondetail.teams.TeamsFragment
import com.jide.soccerflash.ui.home.todaysfixtures.FixturesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompDetailsFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTeamsFragment(): TeamsFragment

    @ContributesAndroidInjector
    abstract fun contributeStandingsFragment(): StandingsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMatchesFragment(): FixturesFragment


    @ContributesAndroidInjector
    abstract fun contributePlayersDialogFragment(): PlayersBottomSheet
}