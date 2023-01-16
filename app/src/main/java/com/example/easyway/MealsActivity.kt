package com.example.easyway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MealsActivity : AppCompatActivity() {


    //Data

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val dateTomorrow = LocalDateTime.now().plusDays(1).format(formatter)

    // Retrofit
    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val mealService = retrofit.create(MealService::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
         val call = mealService.get_all_meals_by_date(dateTomorrow.toString())

        call.enqueue(object:Callback<List<Meal>>
        {
            override fun onResponse(call: Call<List<Meal>>, response: Response<List<Meal>>) {
                if(response.code()==200)
                {
                    val meals = response.body()
                    println(meals)
                }
        }
            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
            println("Meals not available!")
    }
})
    }
}