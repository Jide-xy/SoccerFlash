package com.jide.soccerflash.di

import com.jide.soccerflash.ui.competitiondetail.CompetitionDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompetitionsDetailsActivityModule {

    @ContributesAndroidInjector(modules = [CompDetailsFragmentBuildersModule::class])
    abstract fun contributeCompetitionDetailActivity(): CompetitionDetailActivity
}