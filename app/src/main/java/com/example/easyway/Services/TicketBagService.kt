package com.example.easyway.Services

import com.example.easyway.Models.TicketBag
import com.example.easyway.dtos.AddToTicketBagDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketBagService {

    @POST("/ticketbag/add")
    fun addTicketToTicketBag(@Body addToTicketBagDTO: AddToTicketBagDTO): Call<String>

    @DELETE("/ticketbag/refund")
    fun refundUser(@Body ticketId : String, @Body newUser: String): Call<Boolean>

    @GET("/ticketbag/all")
    fun getTicketBagItems(): Call<List<TicketBag>>
}