package com.side.project.foodapp.di

import com.side.project.foodapp.ui.viewModel.CategoryMealsViewModel
import com.side.project.foodapp.ui.viewModel.MainViewModel
import com.side.project.foodapp.ui.viewModel.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModel = module {
    viewModel { MainViewModel() }
    viewModel { MealViewModel() }
    viewModel { CategoryMealsViewModel() }
}