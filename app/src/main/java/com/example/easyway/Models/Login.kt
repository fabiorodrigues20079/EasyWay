package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

class Login(@SerializedName("email") val email:String, @SerializedName("password") val password:String) {
}