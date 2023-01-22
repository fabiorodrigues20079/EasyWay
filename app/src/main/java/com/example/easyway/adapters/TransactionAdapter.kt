package com.example.easyway.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Ticket
import com.example.easyway.Models.Transaction
import com.example.easyway.R

class TransactionAdapter(private var mList : List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {




    class TransactionViewHolder(inflater: LayoutInflater, val parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.transaction_card, parent, false)){
        var lastBalance = itemView?.findViewById<TextView>(R.id.transaction_card_tv)

        fun bindData(transaction: Transaction) {
           lastBalance?.text = transaction.lastBalance

        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = mList[position]
        holder.bindData(transaction)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun getitem(position: Int): Transaction {
        return mList[position]
    }
}