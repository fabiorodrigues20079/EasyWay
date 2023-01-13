package com.example.easyway.Services

import com.example.easyway.Models.Meal
import retrofit2.Call
import retrofit2.http.GET

interface MealService
{
    @GET("/meal/all")
    fun getAllMeals(): Call<List<Meal>>
}