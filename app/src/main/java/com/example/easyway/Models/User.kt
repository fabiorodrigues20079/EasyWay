package com.example.easyway.Models

import com.google.gson.annotations.SerializedName

// Classe que representa o o token que é uma string
data class User(@SerializedName("token")val token:String,@SerializedName("user") val user:String)



