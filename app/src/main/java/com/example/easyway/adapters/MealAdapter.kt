package com.example.easyway.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Meal
import com.example.easyway.R

class MealAdapter(private var mList : List<Meal>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    class MealViewHolder(inflater: LayoutInflater, val parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.meal_card, parent, false)){
        var priceTv = itemView?.findViewById<TextView>(R.id.meal_price_tv)
        var dishDescTv = itemView?.findViewById<TextView>(R.id.meal_card_title_tv)
        var dateTv = itemView?.findViewById<TextView>(R.id.meal_card_date_tv)

        fun bindData(meal: Meal) {
            val price = "€${meal.price}"
            priceTv?.text = price
            dishDescTv?.text = meal.description
            dateTv?.text = meal.mealDate

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mList[position]
        holder.bindData(meal)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }
}