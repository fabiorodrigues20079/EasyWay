package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

data class TicketBag
    (@SerializedName("cid") val cid : Int,
     @SerializedName("ticketId") val ticketId: String,
     @SerializedName("mealDate") val mealDate : String,
     @SerializedName("price") val price: Double,
     @SerializedName("toRefundIdNumber") val toRefundIdNumber: String,
     @SerializedName("newIdNumber") val newIdNumber: String)
