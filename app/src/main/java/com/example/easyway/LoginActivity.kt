package com.example.easyway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.easyway.Http.HttpHelper
import com.example.easyway.Models.Meal
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    // Declaração de variáveis

    // Retrofit

    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()

    val mealService = retrofit.create(MealService::class.java)
    val ticketService = retrofit.create(TicketService::class.java)

    val email: EditText by lazy {
        findViewById<EditText>(R.id.main_email_et)
    }

    val password: EditText by lazy {
        findViewById<EditText>(R.id.main_password_et)
    }

    val button: Button by lazy {
        findViewById<Button>(R.id.main_login_btn)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val call = mealService.getAllMeals()

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

       // button.setOnClickListener(this)
    }
}
    /*/ Método que será invocado quando o botão for pressionado
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(p0: View?)
    {
        // Tenta fazer login
        try
        {
            var token = HttpHelper.login(email.text.toString(),password.text.toString())
            Toast.makeText(this@LoginActivity,token.token,Toast.LENGTH_LONG).show()
        }

        // Caso não liberta uma exception
        catch (e:java.lang.Exception)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }



    }


    */
