package com.example.easyway.Services

import com.example.easyway.Models.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MealService
{
    @GET("/meal/all")
    fun getAllMeals(): Call<List<Meal>>

    @Headers("Content-Type: application/json")
    @GET("/meal/{date}")
    fun get_all_meals_by_date(@Path("date") date: String): Call<List<Meal>>


    @GET("/meal/week")
    fun getAllMealsForOneWeek(): Call<List<Meal>>

}