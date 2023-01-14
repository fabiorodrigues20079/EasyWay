package com.example.easyway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MealsActivity : AppCompatActivity() {

    // Retrofit

    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val mealService = retrofit.create(MealService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        // val call = mealService.get_all_meals_by_date("2023-01-07")

        /*call.enqueue(object:Callback<List<Meal>>
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
*/
    }
}