package com.example.easyway.Models

import com.google.gson.annotations.SerializedName


// Modelo que representa uma meal
data class Meal(@SerializedName("mealDate")val mealDate:String,
                @SerializedName("price")val price:Double,
                @SerializedName("description")val description:String,
                @SerializedName("MPId")val MPId:Int,
                @SerializedName("Cid")val Cid:Int,
                @SerializedName("Did")val Did:Int,
                )
{
}