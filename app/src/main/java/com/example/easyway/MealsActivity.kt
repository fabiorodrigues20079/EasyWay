package com.example.easyway

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import com.example.easyway.adapters.MealAdapter
import com.example.easyway.adapters.TicketAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MealsActivity : AppCompatActivity() {


    //Obter data de hoje para ir buscar a ementa de hoje
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val tomorrow= LocalDateTime.now().format(formatter)

    //Declaraçao de Variaveis

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

    val ticketBagIcon: ImageView by lazy {
        findViewById<ImageView>(R.id.ticketBag_action_bar)
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


        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)

        val isEmployee = sharedPref.getInt("isEmployee",0)
        if(isEmployee == 1) { // Verifica se é funcionário
            ticketBagIcon.visibility = View.VISIBLE
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mealsRv.layoutManager = linearLayoutManager

        //Obter refeições para hoje
        if(isOnline())
        {
            get_today_meals()
        }
        else
        {
            var IPCAdb = IPCADatabase.getDataBase(this@MealsActivity)

            lifecycleScope.launch(Dispatchers.IO)
            {
                val meals = IPCAdb.MealsDao().getTodaymeals(tomorrow)
                println(meals)
                var auxList :MutableList<Meal> = ArrayList()
                for(meal in meals){
                    println(meal.mealDate)
                    var newMeal = Meal(meal.mealDate,meal.price!!.toDouble(),meal.description!!,meal.MPId!!,meal.Cid!!,meal.Did!!)
                    println(newMeal)
                    auxList.add(newMeal)
                }
                println(auxList)
                auxMeals(auxList)
            }
        }

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

        ticketBagIcon.setOnClickListener{
            val intent = Intent(this@MealsActivity,TicketBagActivity::class.java)
            startActivity(intent)
        }
    }


    fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun get_today_meals(){
        val call = mealService.get_all_meals_by_date(tomorrow.toString())


        call.enqueue(object:Callback<List<Meal>>{
            override fun onResponse(call: Call<List<Meal>>, response: Response<List<Meal>>) {

                if (response.code() == 404) {
                    Toast.makeText(this@MealsActivity, "No meals for today", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val meals = response.body()!!

                }
            }

            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
                println("No meals found")
            }
        })
    }


    fun auxMeals(meals:List<Meal>){
        val adapter = MealAdapter(meals)
        mealsRv.adapter = adapter
        println(meals)

        // Guarda as meals do dia na base de dados local

        for(meal in meals)
        {   var IPCAdb = IPCADatabase.getDataBase(this@MealsActivity)
            lifecycleScope.launch(Dispatchers.IO)
            {
                var meal = com.example.easyway.Entities.Meal(meal.mealDate,meal.price.toString(),meal.Did,meal.MPId,meal.Cid,meal.description)
                IPCAdb.MealsDao().insert(meal)
            }
        }

        adapter.setOnItemClickListener(object : MealAdapter.onItemClickListener {
            override fun onItemclick(position: Int) {
                var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
                val pid = sharedPref.getString("pid", null)
                val result =
                    ticketService.insertTicket(meals.get(position), pid.toString())
                println(result)
                result.enqueue(object : Callback<List<String>> {
                    override fun onResponse(
                        call: Call<List<String>>,
                        response: Response<List<String>>
                    ) {

                        val intent =
                            Intent(this@MealsActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<List<String>>, t: Throwable) {
                        println("error")
                    }
                })
            }
        })
    }
}