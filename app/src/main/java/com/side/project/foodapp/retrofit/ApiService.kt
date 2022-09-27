package com.side.project.foodapp.retrofit

import com.side.project.foodapp.data.MealList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}