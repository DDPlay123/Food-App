package com.side.project.foodapp.di

import com.side.project.foodapp.ui.viewModel.HomeViewModel
import com.side.project.foodapp.ui.viewModel.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModel = module {
    viewModel { HomeViewModel() }
    viewModel { MealViewModel() }
}