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

import androidx.room.Room
import com.jide.soccerflash.SFApplication
import com.jide.soccerflash.api.SFService
import com.jide.soccerflash.repository.Repository
import com.jide.soccerflash.room.db.SFDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRatesService(): SFService {
        return Retrofit.Builder()
            .baseUrl("https://api.football-data.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SFService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: SFApplication): SFDatabase {
        val name = "SoccerFlash_DB"
        return Room.databaseBuilder(
            app.applicationContext,
            SFDatabase::class.java,
            name
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRepository(localDb: SFDatabase, remoteService: SFService): Repository {
        return Repository(localDb, remoteService)
    }
}
