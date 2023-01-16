package com.example.easyway

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import com.example.easyway.adapters.MealAdapter
import com.example.easyway.adapters.TicketAdapter
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
    val tomorrow= LocalDateTime.now().format(formatter)

    val buttonHome: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_home_iv)
    }

    val buttonProfileDetails: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_profile_iv)
    }

    val buttonBalance:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_balance_iv)
    }

    val buttonMeals:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_food_iv)
    }

    val buttonInfo:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_info_iv)
    }


        // Retrofit
    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val mealService = retrofit.create(MealService::class.java)
    val ticketService = retrofit.create(TicketService::class.java)
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
                adapter.setOnItemClickListener(object : MealAdapter.onItemClickListener{
                    override fun onItemclick(position: Int) {
                        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
                        val pid = sharedPref.getString("pid",null)
                        val result = ticketService.insertTicket(meals.get(position),pid.toString())
                        println(result)
                        result.enqueue(object :Callback<List<String>>{
                            override fun onResponse(
                                call: Call<List<String>>,
                                response: Response<List<String>>
                            ) {

                                val intent = Intent(this@MealsActivity,DashboardActivity::class.java)
                                startActivity(intent)
                            }

                            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                                println("error")
                            }
                        })
                    }
                })
            }

            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
                println("No meals found")
            }
        })


        buttonProfileDetails.setOnClickListener {
            val intent = Intent(this,ProfileDetailsActivity::class.java)
            startActivity(intent)
        }

        buttonHome.setOnClickListener {
            val intent = Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }

        buttonBalance.setOnClickListener  {
            val intent = Intent(this@MealsActivity,BalanceActivity::class.java)
            startActivity(intent)
        }

        buttonMeals.setOnClickListener{
            val intent = Intent(this@MealsActivity,WeekMealsActivity::class.java)
            startActivity(intent)
        }

        buttonInfo.setOnClickListener{
            val intent = Intent(this@MealsActivity,TicketActivity::class.java)
            startActivity(intent)
        }
    }
}