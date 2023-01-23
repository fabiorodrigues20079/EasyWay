package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

data class Person   (@SerializedName("pid") val Pid:Int?,
                     @SerializedName("name") val name:String,
                     @SerializedName("hash_password") val hashPassword:String,
                     @SerializedName("email") val email:String,
                     @SerializedName("balance") val Balance:Double,
                     @SerializedName("isDisabled") val isDisabled: Int,
                     @SerializedName("isEmployee") val isEmployee: Int,
                     @SerializedName("phoneNumber") val phoneNumber: String,
                     @SerializedName("idNumber") val idNumber:String?)
