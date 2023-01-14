package com.example.easyway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


class MealsActivity : AppCompatActivity() {

    // Retrofit

    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val mealService = retrofit.create(MealService::class.java)

    // Layout
    val mealsRv by lazy { findViewById<RecyclerView>(R.id.meals_meals_rv)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mealsRv.layoutManager = linearLayoutManager
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
        val call = mealService.getAllMealsForOneWeek()
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