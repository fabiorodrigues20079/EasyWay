package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Responses.RefundTicketResponse
import com.example.easyway.Models.Ticket
import com.example.easyway.Models.TicketBag
import com.example.easyway.Services.TicketBagService
import com.example.easyway.Services.TicketService
import com.example.easyway.adapters.TicketAdapter
import com.example.easyway.adapters.TicketBagAdapter
import com.example.easyway.dtos.RefundTicketDTO
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TicketBagActivity : AppCompatActivity() {







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

    val ticketBagRv : RecyclerView by lazy { findViewById<RecyclerView>(R.id.ticketbag_tickets_rv)}

    val baseURL = "http://10.0.2.2:5000/"

    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val ticketBagService = retrofit.create(TicketBagService::class.java)

    val refundEt by lazy { findViewById<EditText>(R.id.ticketBag_card_idNumber_et)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_bag)

        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
        val isEmployee = sharedPref.getInt("isEmployee",0)
        if(isEmployee == 1) {
            ticketBagIcon.visibility = View.VISIBLE
        }


        buttonProfileDetails.setOnClickListener {
            val intent = Intent(this@TicketBagActivity,ProfileDetailsActivity::class.java)
            startActivity(intent)
        }

        buttonBalance.setOnClickListener  {
                val intent = Intent(this@TicketBagActivity,BalanceActivity::class.java)
            startActivity(intent)
        }

        buttonMeals.setOnClickListener{
            val intent = Intent(this@TicketBagActivity,WeekMealsActivity::class.java)
            startActivity(intent)
        }



        buttonInfo.setOnClickListener{
            val intent = Intent(this@TicketBagActivity,TicketActivity::class.java)
            startActivity(intent)
        }
        getTicketBag()
        ticketBagRv.layoutManager = LinearLayoutManager(this@TicketBagActivity)
    }


    fun getTicketBag(){
        val call = ticketBagService.getTicketBagItems()


        call.enqueue(object: Callback<List<TicketBag>> {
            override fun onResponse(call: Call<List<TicketBag>>, response: Response<List<TicketBag>>) {
                if(response.code()!=200)
                {
                    Toast.makeText(this@TicketBagActivity,"User has no tickets!", Toast.LENGTH_LONG).show()
                }

                else {
                    //Adicionar à recycler view
                    val ticketBags = response.body()!!
                    println(ticketBags)
                    val adapter = TicketBagAdapter(ticketBags)
                    ticketBagRv.adapter = adapter
                    adapter.setOnItemClickListener(object : TicketBagAdapter.onItemClickListener {
                        override fun onItemclick(position: Int, idNumber: String) {
                            val result = ticketBagService.refundUser(RefundTicketDTO(ticketBags[position].ticketId,ticketBags[position].price,ticketBags[position].toRefundIdNumber, idNumber))
                            println(result)
                            result.enqueue(object : Callback<RefundTicketResponse> {
                                override fun onResponse(
                                    call: Call<RefundTicketResponse>,
                                    response: Response<RefundTicketResponse>
                                ) {
                                    Toast.makeText(
                                        this@TicketBagActivity,
                                        "Ticket refunded",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    var intent = Intent(this@TicketBagActivity, DashboardActivity::class.java)
                                    startActivity(intent)
                                }

                                override fun onFailure(call: Call<RefundTicketResponse>, t: Throwable) {
                                    throw t
                                }
                            })
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<TicketBag>>, t: Throwable) {
                Toast.makeText(
                    this@TicketBagActivity,
                    "No tickets available",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }


}