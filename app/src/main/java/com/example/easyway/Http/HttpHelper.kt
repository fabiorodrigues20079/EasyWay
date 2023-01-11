package com.example.easyway.Http

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.easyway.Models.Token
import com.google.gson.Gson
import okhttp3.*


class HttpHelper()
{
    companion object
    {
        @RequiresApi(Build.VERSION_CODES.N)
        fun login(email:String, password:String):Token
        {
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(BasicAuthInterceptor(email,password)).build()
            val request:Request = Request.Builder().url("http://10.0.2.2:5000/login").post(RequestBody.create(null,"")).build()
            var token:Token?=null
            val callBackFuture:CallBackFuture= CallBackFuture()

            client.newCall(request).enqueue(callBackFuture)

            var response:Response = callBackFuture.get()

            if(response.code()==200)
            {
                val gson = Gson()
               token=gson.fromJson(response.body()?.string(),Token::class.java)
                return token!!
            }

            else
            {
                throw  Exception("Wrong Credentials!")
            }
        }
    }
}