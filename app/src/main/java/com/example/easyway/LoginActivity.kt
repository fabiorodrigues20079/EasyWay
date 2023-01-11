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

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    // Declaração de variáveis
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
        button.setOnClickListener(this)

    }

    // Método que será invocado quando o botão for pressionado
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


}