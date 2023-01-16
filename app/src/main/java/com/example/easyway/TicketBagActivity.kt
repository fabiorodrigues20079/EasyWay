package com.example.easyway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TicketBagActivity : AppCompatActivity() {

    val buttonHome: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_home_iv)
    }

    val buttonProfileDetails: ImageView by lazy {
        findViewById<ImageView>(R.id.bar_profile_iv)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_bag)
    }
}