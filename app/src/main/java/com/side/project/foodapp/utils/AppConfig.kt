package com.side.project.foodapp.utils

import android.app.Application
import com.side.project.foodapp.di.daoModule
import com.side.project.foodapp.di.dbModule
import com.side.project.foodapp.di.repoModule
import com.side.project.foodapp.di.viewModelModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(
                viewModelModel,
                daoModule,
                dbModule,
                repoModule
            ))
        }
    }
}