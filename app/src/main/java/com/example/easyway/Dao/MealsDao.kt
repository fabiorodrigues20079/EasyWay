package com.example.easyway.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyway.Entities.Meal


@Dao
interface MealsDao
{
    @Query("SELECT * FROM meal")
    fun getAllMeals(): List<Meal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(meal: Meal)
}