package com.example.easyway.Services

import com.example.easyway.Models.TicketBag
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketBagService {

    @POST("/ticketBag/addTicket")
    fun addTicketToTicketBag(@Body loggedUserNumber: String, @Body ticketId: String, @Body mealDate: String, @Body price : Double): Call<Boolean>

    @DELETE("/ticketBag/refund")
    fun refundUser(@Body ticketId : String, @Body newUser: String): Call<Boolean>

    @GET("/ticketBag/all")
    fun getTicketBagItems(): Call<List<TicketBag>>
}