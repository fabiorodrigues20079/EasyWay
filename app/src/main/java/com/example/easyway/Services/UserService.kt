package com.example.easyway.Services

import com.example.easyway.Models.Meal
import com.example.easyway.Models.Person
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/user/balance/{id}")
    fun userBalance(@Path("id") id: String): Call<List<String>>

    @GET("/user/all")
    fun getAllUsers():Call<List<Person>>
}