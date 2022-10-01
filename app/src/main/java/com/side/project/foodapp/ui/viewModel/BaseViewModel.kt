package com.side.project.foodapp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.data.repo.MealRepo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel: ViewModel(), KoinComponent {
    private val mealRepo: MealRepo by inject()

    fun insertMeal(meal: Meal) = mealRepo.insertMeal(meal)

    fun deleteMeal(meal: Meal) = mealRepo.deleteMeal(meal)

    fun getAllMeals(): List<Meal> = mealRepo.getAllMeals()
}