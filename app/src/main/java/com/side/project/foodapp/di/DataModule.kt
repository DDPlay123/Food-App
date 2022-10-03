package com.side.project.foodapp.di

import androidx.room.Room
import com.side.project.foodapp.data.db.MealDb
import com.side.project.foodapp.data.repo.MealRepo
import com.side.project.foodapp.data.repo.MealRepoImpl
import com.side.project.foodapp.utils.AnimManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val daoModule = module {
    single { get<MealDb>().mealDao() }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MealDb::class.java,
            MealDb::class.java.simpleName
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val repoModule = module {
    single<MealRepo> { MealRepoImpl() }
}

val managerModule = module {
    single { AnimManager(androidContext()) }
}

