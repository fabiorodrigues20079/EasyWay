package com.example.easyway.Services

import com.example.easyway.Models.Ticket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketService
{
    // Método que permite dar um get a todos os tickets
    @GET("/ticket/all")
    fun getAllTickets(): Call<List<Ticket>>
}