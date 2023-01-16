package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class BalanceActivity : AppCompatActivity() {

    val buttonHome: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_home_iv)
    }

    val buttonProfileDetails: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_profile_iv)
    }

    val buttonMeals:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_food_iv)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

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
}
    }