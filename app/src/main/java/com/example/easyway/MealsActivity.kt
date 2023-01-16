package com.example.easyway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import com.example.easyway.adapters.MealAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MealsActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val tomorrow= LocalDateTime.now().plusDays(1).format(formatter)

    // Retrofit
    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val mealService = retrofit.create(MealService::class.java)
    // Layout
    val mealsRv by lazy { findViewById<RecyclerView>(R.id.meals_meals_rv)}


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mealsRv.layoutManager = linearLayoutManager
        val call = mealService.get_all_meals_by_date(tomorrow.toString())

        call.enqueue(object:Callback<List<Meal>>{
            override fun onResponse(call: Call<List<Meal>>, response: Response<List<Meal>>) {
                //Adicionar à recycler view
                val meals = response.body()!!
                val adapter = MealAdapter(meals)
                mealsRv.adapter = adapter
                println(meals)
            }

            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
                println("No meals found")
            }
        })
    }
}