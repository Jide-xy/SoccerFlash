/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jide.soccerflash.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jide.soccerflash.ui.competitiondetail.standings.StandingsViewModel
import com.jide.soccerflash.ui.competitiondetail.teams.TeamsViewModel
import com.jide.soccerflash.ui.home.competitions.CompetitionViewModel
import com.jide.soccerflash.ui.home.todaysfixtures.FixturesViewModel
import com.jide.soccerflash.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FixturesViewModel::class)
    abstract fun bindHomeViewModel(fixturesViewModel: FixturesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompetitionViewModel::class)
    abstract fun bindCompetitionsViewModel(competitionsViewModel: CompetitionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StandingsViewModel::class)
    abstract fun bindStandingsViewModel(standingsViewModel: StandingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamsViewModel::class)
    abstract fun bindTeamsViewModel(teamsViewModel: TeamsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
