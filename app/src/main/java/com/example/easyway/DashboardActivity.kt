package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView



class DashboardActivity : AppCompatActivity() {
    val welcomeTv: TextView by lazy {
        findViewById<TextView>(R.id.dashboard_welcome_tv)
    }

    val buttonToday: Button by lazy {
        findViewById<Button>(R.id.dash_today_bt)
    }

    val buttonTomorrow: Button by lazy {
        findViewById<Button>(R.id.dashboard_tomorrowFood_btn)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var name = intent.getStringExtra("name")!!
        name = name.substring(1,name.length-1)
        welcomeTv.setText("Welcome $name")

        buttonToday.setOnClickListener {
            val intent = Intent(this,MealsActivity::class.java)
            startActivity(intent)
        }

        buttonTomorrow.setOnClickListener {
            val intent = Intent(this,MealsActivity::class.java)
            startActivity(intent)
        }
    }
}