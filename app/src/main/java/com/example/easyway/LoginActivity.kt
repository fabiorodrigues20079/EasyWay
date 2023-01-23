package com.example.easyway

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.easyway.Dao.PersonDao
import com.example.easyway.Entities.UserInfo
import com.example.easyway.Models.Login
import com.example.easyway.Models.User
import com.example.easyway.Services.LoginService
import com.example.easyway.Services.MealService
import com.example.easyway.Services.TicketService
import com.example.easyway.Models.Person
import com.example.easyway.Services.UserService
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {



    // Retrofit
    val baseURL = "http://10.0.2.2:5000/"
    var retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()

    val loginService = retrofit.create(LoginService::class.java)
    val userService = retrofit.create(UserService::class.java)

    // Declaração de variáveis
    val ticketBagIcon: ImageView by lazy { findViewById<ImageView>(R.id.ticketBag_action_bar) }
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

        // Se tiver internet faz login através da API, senão usa base de dados local


        button.setOnClickListener{
            if(isOnline())
            {
                addUsersToDB()
                login(email.text.toString(),password.text.toString())
            }
            else
            {
                loginDB(email.text.toString(),password.text.toString())
            }
        }
    }



    //Função para fazer o login através da API (ONLINE)
    fun login(email:String,password:String){
        // Vai ao service de login buscar o método login
        val log = loginService.Login(Login(email,password))

        log.enqueue(object :Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.code() == 200){
                    val logged = response.body()
                    val user = logged?.get(0)?.user
                    val jsonParser = JsonParser()
                    val jsonObject = jsonParser.parse(user)
                    val newUser = jsonObject.asJsonArray.get(0).asJsonObject
                    var name = newUser.get("name").toString()
                    name = name.substring(1,name.length-1)
                    var email = newUser.get("email").toString()
                    email = email.substring(1,email.length-1)
                    var idNumber = newUser.get("idNumber").toString()
                    idNumber = idNumber.substring(1,idNumber.length-1)
                    var pid = newUser.get("pid").toString()
                    var isEmployee = newUser.get("isEmployee").toString()
                    // Utilização de SharedPreferences
                    addToSharedPreference(name,email,idNumber,pid,isEmployee)
                    val intent = Intent(this@LoginActivity,DashboardActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this@LoginActivity,"Incorrect Credentials",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Incorrect Credentials",Toast.LENGTH_LONG).show()
            }
        })
    }


    // Função para fazer o login através da base de dados local
    fun loginDB(email:String,password:String)
    {  var IPCAdb = IPCADatabase.getDataBase(this@LoginActivity)
        lifecycleScope.launch(Dispatchers.IO)
        {
            val users = IPCAdb.userInfoDao().checkLogin(email)
            val person = IPCAdb.PersonDao().getPerson(users[0].pid!!)
            addToSharedPreference(person[0].name!!,users[0].email!!,person[0].idNumber,person[0].pid.toString(),users[0].isEmployee.toString())

            if(users[0].email == email && users[0].hashPassword== password)
            {

                val intent = Intent(this@LoginActivity,DashboardActivity::class.java)
                startActivity(intent)
            }

        }
    }


    // Função que irá permitir,sempre que se entrar na página login, guardar todos os utilizadores existentes na base de dados
    fun addUsersToDB()
    {
         val call = userService.getAllUsers()

        call.enqueue(object: Callback<List<com.example.easyway.Models.Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                var persons = response.body()!!
                for(person in persons)
                {   var IPCAdb = IPCADatabase.getDataBase(this@LoginActivity)
                    lifecycleScope.launch(Dispatchers.IO)
                    {   println(person)
                        var persona = com.example.easyway.Entities.Person(person.Pid,person.name,
                        person.isDisabled,person.phoneNumber,person.idNumber)
                        var userInfo = UserInfo(person.Pid,person.email,person.hashPassword,
                            person.isEmployee,person.Balance,null)
                        IPCAdb.userInfoDao().insert(userInfo)
                        IPCAdb.PersonDao().insert(persona)

                    }
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                println("No users!")
            }
        })
    }

    // Função que verifica se existe conexão à internet
    fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }


    fun addToSharedPreference(name:String,email:String,idNumber:String?,pid:String?,isEmployee:String){
        val sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply()
        {
            putString("name",name)
            putString("email",email)
            putString("idNumber",idNumber)
            putString("pid",pid)
            putInt("isEmployee", isEmployee.toInt())
            apply()
        }
    }

}

