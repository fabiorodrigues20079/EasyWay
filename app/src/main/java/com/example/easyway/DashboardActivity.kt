package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView



class DashboardActivity : AppCompatActivity() {

    // Variáveis

    val welcomeTv: TextView by lazy {
        findViewById<TextView>(R.id.dashboard_welcome_tv)
    }

    val buttonToday: Button by lazy {
        findViewById<Button>(R.id.dash_today_bt)
    }

    val buttonTomorrow: Button by lazy {
        findViewById<Button>(R.id.dashboard_tomorrowFood_btn)
    }

    val buttonProfileDetails: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_profile_iv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
        val name = sharedPref.getString("name",null)

        welcomeTv.setText("Welcome $name")

        buttonToday.setOnClickListener {
            val intent = Intent(this,MealsActivity::class.java)
            startActivity(intent)
        }

        buttonTomorrow.setOnClickListener {
            val intent = Intent(this,MealsActivity::class.java)
            startActivity(intent)
        }

        buttonProfileDetails.setOnClickListener {
            val intent = Intent(this,ProfileDetailsActivity::class.java)
            startActivity(intent)
        }


    }
}