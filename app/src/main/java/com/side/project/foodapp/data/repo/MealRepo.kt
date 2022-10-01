package com.side.project.foodapp.data.repo

import com.side.project.foodapp.data.db.MealDao
import com.side.project.foodapp.data.model.Meal
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MealRepo {
    fun insertMeal(meal: Meal)
    fun deleteMeal(meal: Meal)
    fun getAllMeals(): List<Meal>
}

class MealRepoImpl: MealRepo, KoinComponent {
    private val mealDao: MealDao by inject()

    override fun insertMeal(meal: Meal) = runBlocking { mealDao.insert(meal) }

    override fun deleteMeal(meal: Meal) = runBlocking { mealDao.delete(meal) }

    override fun getAllMeals(): List<Meal> = mealDao.getAllMeals()
}