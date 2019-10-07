package com.jide.soccerflash.di

import com.jide.soccerflash.ui.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}
