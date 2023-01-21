package com.example.easyway.Services

import com.example.easyway.Models.Responses.RefundTicketResponse
import com.example.easyway.Models.TicketBag
import com.example.easyway.dtos.AddToTicketBagDTO
import com.example.easyway.dtos.RefundTicketDTO
import retrofit2.Call
import retrofit2.http.*

interface TicketBagService {

    @POST("/ticketbag/add")
    fun addTicketToTicketBag(@Body addToTicketBagDTO: AddToTicketBagDTO): Call<String>

    @POST("/ticketbag/refund")
    @Headers("Content-Type: application/json")
    fun refundUser(@Body refundTicketDTO: RefundTicketDTO): Call<RefundTicketResponse>

    @GET("/ticketbag/all")
    fun getTicketBagItems(): Call<List<TicketBag>>
}