package com.example.easyway.Services

import com.example.easyway.Models.Login
import com.example.easyway.Models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("/login")
    fun Login(@Body log:Login): Call<List<User>>
}

