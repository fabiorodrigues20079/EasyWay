package com.example.easyway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfileDetailsActivity : AppCompatActivity() {

    // Variáveis

    val nameTextView:TextView by lazy {
        findViewById<TextView>(R.id.tv_profile_name)
    }

    val emailTextView:TextView by lazy {
        findViewById<TextView>(R.id.tv_profile_email)
    }

    val numberTextView:TextView by lazy {
        findViewById<TextView>(R.id.tv_profile_number)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)

        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
        val name = sharedPref.getString("name",null)
        val email = sharedPref.getString("email",null)
        val number = sharedPref.getString("idNumber",null)

        nameTextView.setText("Name: $name")
        emailTextView.setText("Email: $email")
        numberTextView.setText("Number: $number")




    }
}