package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

data class Ticket(@SerializedName("mealDate")val mealDate:String,
                  @SerializedName("price")val price:Double,
                  @SerializedName("description")val description:String,
                  @SerializedName("MealPeriod")val MealPeriod:String,
                  @SerializedName("CanteenName")val CanteenName:String,
)