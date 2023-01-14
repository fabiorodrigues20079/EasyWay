package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

data class UserInfo(@SerializedName("email") val email:String,
               @SerializedName("name") val name:String,
                @SerializedName("pid") val pid: Int,
               @SerializedName("idNumber") val idNumber: Int,
                @SerializedName("isEmployee") val isEmployee:Int)

{

}