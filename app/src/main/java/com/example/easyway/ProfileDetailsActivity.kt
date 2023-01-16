package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
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

    val buttonHome: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_home_iv)
    }

    val buttonBalance:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_balance_iv)
    }

    val buttonMeals:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_food_iv)
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


        buttonHome.setOnClickListener {
            val intent = Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }

        buttonBalance.setOnClickListener  {
            val intent = Intent(this@ProfileDetailsActivity,BalanceActivity::class.java)
            startActivity(intent)
        }

        buttonMeals.setOnClickListener{
            val intent = Intent(this@ProfileDetailsActivity,WeekMealsActivity::class.java)
            startActivity(intent)
        }


    }
}