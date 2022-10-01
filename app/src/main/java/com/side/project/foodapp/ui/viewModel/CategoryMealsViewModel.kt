package com.side.project.foodapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.side.project.foodapp.data.model.MealsByCategory
import com.side.project.foodapp.data.model.MealsByCategoryList
import com.side.project.foodapp.network.ApiClient
import com.side.project.foodapp.utils.logE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: BaseViewModel() {
    private var mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    // Observer Data
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> = mealsLiveData

    // Functional
    fun getMealsByCategory(categoryName: String) {
        ApiClient.getRetrofit.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                response.body()?.let { mealsLiveData.value = it.meals }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                logE("GetMealsByCategory", t.message.toString())
            }
        })
    }
}