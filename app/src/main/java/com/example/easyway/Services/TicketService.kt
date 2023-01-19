package com.example.easyway.Services

import com.example.easyway.Models.Meal
import com.example.easyway.Models.Ticket
import retrofit2.Call
import retrofit2.http.*


interface TicketService
{
    // Método que permite dar um get a todos os tickets
    @GET("/ticket/all")
    fun getAllTickets(): Call<List<Ticket>>

    @GET("/ticket/all/{id}")
    fun getClientTickets(@Path("id") id:String): Call<List<Ticket>>

    @Headers("Content-Type: application/json")
    @POST("/ticket/buy/{id}")
    fun insertTicket(@Body body: Meal,@Path("id") id:String): Call<List<String>>

    @DELETE("/ticket/{id}")
    fun deleteTicket(@Path("id") id: String): Call<List<String>>
}