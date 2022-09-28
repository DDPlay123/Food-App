package com.side.project.foodapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.side.project.foodapp.data.Meal
import com.side.project.foodapp.data.MealList
import com.side.project.foodapp.network.ApiClient
import com.side.project.foodapp.utils.logE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    // Observer Data
    fun observeMealDetailLiveData(): LiveData<Meal> = mealDetailLiveData

    // Functional
    fun getMealDetail(id: String) {
        ApiClient.getRetrofit.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null)
                    mealDetailLiveData.value = response.body()!!.meals[0]
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                logE("GetMealDetail", t.message.toString())
            }
        })
    }
}