package com.side.project.foodapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.side.project.foodapp.data.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: Meal)

    @Delete
    fun delete(meal: Meal)

    @Query("SELECT * FROM MealInfo")
    fun getAllMeals(): LiveData<List<Meal>>
}