package com.side.project.foodapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.side.project.foodapp.data.model.*
import com.side.project.foodapp.network.ApiClient
import com.side.project.foodapp.utils.Coroutines
import com.side.project.foodapp.utils.logE
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: BaseViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = getAllMeals()

    // Observer Data
    fun observeRandomMealLiveData(): LiveData<Meal> = randomMealLiveData

    fun observePopularItemLiveData(): LiveData<List<MealsByCategory>> = popularItemLiveData

    fun observeCategoriesLiveData(): LiveData<List<Category>> = categoriesLiveData

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> = favoritesMealsLiveData

    // Functional
    fun getRandomMeal() {
        ApiClient.getRetrofit.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let { randomMealLiveData.value = it.meals[0] }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                logE("GetRandomMeal", t.message.toString())
            }
        })
    }

    fun getPopularItems() {
        ApiClient.getRetrofit.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                    response.body()?.let { popularItemLiveData.value = it.meals }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    logE("GetPopularItems", t.message.toString())
                }
            })
    }

    fun getCategories() {
        ApiClient.getRetrofit.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoriesLiveData.value = it.categories }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                logE("GetCategories", t.message.toString())
            }
        })
    }
}