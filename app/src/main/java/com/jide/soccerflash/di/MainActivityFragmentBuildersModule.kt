/*
 * Copyright (C) 2017 The Android Open Source Project
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

import com.jide.soccerflash.ui.home.competitions.CompetitionsFragment
import com.jide.soccerflash.ui.home.todaysfixtures.FixturesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import java.lang.annotation.Documented
import javax.inject.Scope

@Scope
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Module
abstract class MainActivityFragmentBuildersModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMatchesFragment(): FixturesFragment

    @ContributesAndroidInjector
    abstract fun contributeFragment(): CompetitionsFragment
}
