package com.side.project.foodapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.side.project.foodapp.data.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM MealInfo")
    fun getAllMeals(): List<Meal>
}