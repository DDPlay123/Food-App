package com.side.project.foodapp.network

import com.side.project.foodapp.data.CategoryList
import com.side.project.foodapp.data.MealsByCategoryList
import com.side.project.foodapp.data.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("filter.php?")
    fun getMealsByArea(@Query("a") areaName: String): Call<MealsByCategoryList>
}