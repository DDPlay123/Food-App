package com.side.project.foodapp.data.repo

import androidx.lifecycle.LiveData
import com.side.project.foodapp.data.db.MealDao
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.utils.Coroutines
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MealRepo {
    fun insertMeal(meal: Meal)
    fun deleteMeal(meal: Meal)
    fun getAllMeals(): LiveData<List<Meal>>
}

class MealRepoImpl: MealRepo, KoinComponent {
    private val mealDao: MealDao by inject()

    override fun insertMeal(meal: Meal) { Coroutines.io { mealDao.insert(meal) } }

    override fun deleteMeal(meal: Meal) { Coroutines.io { mealDao.delete(meal) } }

    override fun getAllMeals(): LiveData<List<Meal>> = mealDao.getAllMeals()
}