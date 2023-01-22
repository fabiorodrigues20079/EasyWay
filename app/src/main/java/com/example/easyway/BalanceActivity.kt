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
import com.example.easyway.Models.Transaction
import com.example.easyway.Services.*
import com.example.easyway.adapters.MealAdapter
import com.example.easyway.adapters.TransactionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BalanceActivity : AppCompatActivity() {
        // Retrofit
        val ticketBag: ImageView by lazy { findViewById<ImageView>(R.id.ticketBag_action_bar) }
        val baseURL = "http://10.0.2.2:5000/"
        var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
        val BalanceService = retrofit.create(UserService::class.java)
        val TransactionService = retrofit.create(TransactionService::class.java)

    // Layout
        val transactionsRv by lazy { findViewById<RecyclerView>(R.id.transaction_rv)}

        //Declaração de Variáveis
        val ticketBagIcon: ImageView by lazy { findViewById<ImageView>(R.id.ticketBag_action_bar) }
        val balanceTv: TextView by lazy {
            findViewById<TextView>(R.id.tv_balance_cash)
        }
        val buttonHome: ImageView by lazy {
            findViewById<ImageView>(R.id.bar_home_iv)
        }

        val buttonProfileDetails: ImageView by lazy {
            findViewById<ImageView>(R.id.bar_profile_iv)
        }

        val buttonMeals:ImageView by lazy {
            findViewById<ImageView>(R.id.bar_food_iv)
        }

        val buttonInfo:ImageView by lazy {
            findViewById<ImageView>(R.id.bar_info_iv)
        }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_balance)

            //Acessar os dados da SharedPreferences
            var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
            val number = sharedPref.getString("idNumber",null)
            val isEmployee = sharedPref.getInt("isEmployee",0)
            if(isEmployee == 1) {
                ticketBagIcon.visibility = View.VISIBLE
            }
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            transactionsRv.layoutManager = linearLayoutManager
            getbalance(number.toString())

                val call = TransactionService.previousBalance(number.toString())
                call.enqueue(object :Callback<List<Transaction>>{
                    override fun onResponse(
                        call: Call<List<Transaction>>,
                        response: Response<List<Transaction>>
                    ) {
                        val transaction = response.body()!!
                        val adapter = TransactionAdapter(transaction)
                        transactionsRv.adapter = adapter
                        println(transaction)
                    }

                    override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                        Toast.makeText(this@BalanceActivity,"No transactions available!",Toast.LENGTH_LONG).show()
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

            buttonMeals.setOnClickListener{
                val intent = Intent(this@BalanceActivity,WeekMealsActivity::class.java)
                startActivity(intent)
            }

            buttonInfo.setOnClickListener{
                val intent = Intent(this@BalanceActivity,TicketActivity::class.java)
                startActivity(intent)
            }
        }

        //Função para obter o salda de conta do cliente
        fun getbalance(id:String){
            val call = BalanceService.userBalance(id!!)

            call.enqueue(object: Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    //Adicionar à recycler view
                    var balance = response.body()!!
                    var newBalance = balance.get(0)
                    balanceTv.setText("€$newBalance")

                }
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    println("No balance found")
                }
            })

        }


    }