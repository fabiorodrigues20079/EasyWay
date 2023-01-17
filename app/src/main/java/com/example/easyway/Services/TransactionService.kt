package com.example.easyway.Services

import com.example.easyway.Models.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionService
{
    @GET("/transaction/previousBalance/{id}")
    fun previousBalance(@Path("id") id: String): Call<List<Transaction>>

}