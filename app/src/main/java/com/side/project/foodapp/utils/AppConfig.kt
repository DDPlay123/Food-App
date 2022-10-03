package com.side.project.foodapp.utils

import android.app.Application
import com.side.project.foodapp.di.*
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
                managerModule,
                repoModule
            ))
        }
    }
}