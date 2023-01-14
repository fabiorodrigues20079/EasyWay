package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.easyway.Models.Login
import com.example.easyway.Models.User
import com.example.easyway.Services.LoginService
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import com.google.gson.JsonParser
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
    val loginService = retrofit.create(LoginService::class.java)

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


        button.setOnClickListener{
            login(email.text.toString(),password.text.toString())
        }







       // button.setOnClickListener(this)
    }

    fun login(email:String,password:String){
        val log = loginService.Login(Login(email,password))

        log.enqueue(object :Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.code() == 200){
                    val logged = response.body()
                    val user = logged?.get(0)?.user
                    val jsonParser = JsonParser()
                    val jsonObject = jsonParser.parse(user)
                    val newUser = jsonObject.asJsonArray.get(0).asJsonObject
                    val intent = Intent(this@LoginActivity,DashboardActivity::class.java)
                    intent.putExtra("name",newUser.get("name").toString())
                    startActivity(intent)
                    print("viva")
                }
                else{
                    Toast.makeText(this@LoginActivity,"Incorrect Credentials",Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Incorrect Credentials",Toast.LENGTH_LONG).show()
            }
        })
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
