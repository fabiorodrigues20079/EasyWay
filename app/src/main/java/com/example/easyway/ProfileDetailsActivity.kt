package com.example.easyway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    val buttonInfo:ImageView by lazy {
        findViewById<ImageView>(R.id.bar_info_iv)
    }

    val ticketBagIcon: ImageView by lazy {
        findViewById<ImageView>(R.id.ticketBag_action_bar)
    }


        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_profile_details)

        //obter dados da sharedpreferences
                var sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
                val name = sharedPref.getString("name",null)
                val email = sharedPref.getString("email",null)
                val number = sharedPref.getString("idNumber",null)
                nameTextView.setText("Name: $name")
                emailTextView.setText("Email: $email")
                numberTextView.setText("Number: $number")


            val isEmployee = sharedPref.getInt("isEmployee",0)
            if(isEmployee == 1) {
                ticketBagIcon.visibility = View.VISIBLE
            }

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

            buttonInfo.setOnClickListener{
                val intent = Intent(this@ProfileDetailsActivity,TicketActivity::class.java)
                startActivity(intent)
        }
            ticketBagIcon.setOnClickListener{
                val intent = Intent(this@ProfileDetailsActivity,TicketBagActivity::class.java)
                startActivity(intent)
            }

    }
}