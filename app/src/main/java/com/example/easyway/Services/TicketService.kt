package com.example.easyway.Services

import com.example.easyway.Models.Ticket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketService
{
    // Método que permite dar um get a todos os tickets
    @GET("/ticket/all")
    fun getAllTickets(): Call<List<Ticket>>

    @GET("/ticket/all/{id}")
    fun getClientTickets(@Path("id") id:Int): Call<List<Ticket>>

    @POST("/ticket")
    fun insertTicket(@Body date: String,@Body mpid: Int,@Body pid: Int,@Body did: Int,@Body cid: Int)

    @DELETE("ticket/{id}")
    fun deleteTicket(@Path("id") id: Int )
}