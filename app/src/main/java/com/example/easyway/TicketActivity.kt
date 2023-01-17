package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Meal
import com.example.easyway.Models.Ticket
import com.example.easyway.Services.LoginService
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import com.example.easyway.adapters.MealAdapter
import com.example.easyway.adapters.TicketAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TicketActivity : AppCompatActivity() {

    val mealsTv: TextView by lazy {
        findViewById<TextView>(R.id.ticket_meals_tv)
    }

    val priceTv:TextView by lazy {
        findViewById<TextView>(R.id.ticket_price_tv)
    }

    val dateTv:TextView by lazy {
        findViewById<TextView>(R.id.ticket_card_date_tv)
    }

    val buttonProfileDetails: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_profile_iv)
    }

    val buttonHome: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_home_iv)
    }

    val buttonBalance: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_balance_iv)
    }

    val buttonMeals: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_food_iv)
    }

    val buttonInfo: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_info_iv)
    }

    val ticketBagIcon: ImageView by lazy { findViewById<ImageView>(R.id.ticketBag_action_bar) }
    // Retrofit
    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val ticketService = retrofit.create(TicketService::class.java)


    val ticketRv by lazy { findViewById<RecyclerView>(R.id.ticket_meals_rv)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val linearLayoutManager = LinearLayoutManager(this@TicketActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        ticketRv.layoutManager = linearLayoutManager
        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
        val pid = sharedPref.getString("pid",null)


        val isEmployee = sharedPref.getInt("isEmployee",0)

        if(isEmployee == 1) {
            ticketBagIcon.visibility = View.VISIBLE
        }
        getTickets(pid.toString())


        buttonProfileDetails.setOnClickListener {
            val intent = Intent(this@TicketActivity,ProfileDetailsActivity::class.java)
            startActivity(intent)
        }

        buttonBalance.setOnClickListener  {
            val intent = Intent(this@TicketActivity,BalanceActivity::class.java)
            startActivity(intent)
        }

        buttonMeals.setOnClickListener{
            val intent = Intent(this@TicketActivity,WeekMealsActivity::class.java)
            startActivity(intent)
        }


        buttonInfo.setOnClickListener{
            val intent = Intent(this@TicketActivity,TicketActivity::class.java)
            startActivity(intent)
        }

        buttonHome.setOnClickListener {
            val intent = Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }

    }

    fun getTickets(pid:String){
        val call = ticketService.getClientTickets(pid!!)

        call.enqueue(object: Callback<List<Ticket>> {
            override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if(response.code()!=200)
                {
                    Toast.makeText(this@TicketActivity,"User has no tickets!",Toast.LENGTH_LONG).show()
                }

                else {
                    //Adicionar à recycler view
                    val tickets = response.body()!!
                    println(tickets)
                    val adapter = TicketAdapter(tickets)
                    ticketRv.adapter = adapter
                    adapter.setOnItemClickListener(object : TicketAdapter.onItemClickListener {
                        override fun onItemclick(position: Int) {
                            val result = ticketService.deleteTicket(tickets.get(position).id)
                            println(result)
                            result.enqueue(object : Callback<List<String>> {
                                override fun onResponse(
                                    call: Call<List<String>>,
                                    response: Response<List<String>>
                                ) {
                                    Toast.makeText(
                                        this@TicketActivity,
                                        "Ticket Removed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@TicketActivity, DashboardActivity::class.java)
                                    startActivity(intent)
                                }

                                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                                    Toast.makeText(
                                        this@TicketActivity,
                                        "User has no tickets!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                println("No tickets")
            }
        })

    }
}