package com.example.easyway.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Ticket
import com.example.easyway.Models.TicketBag
import com.example.easyway.R

class TicketBagAdapter(private var mList : List<TicketBag>) : RecyclerView.Adapter<TicketBagAdapter.TicketBagViewHolder>() {
    private lateinit var myListener: onItemClickListener
    public interface onItemClickListener{

        fun onItemclick(position: Int, idNumber: String){

        }

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    class TicketBagViewHolder(inflater: LayoutInflater, val parent: ViewGroup, listener: onItemClickListener) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.ticket_card, parent, false)){
        var priceTv = itemView?.findViewById<TextView>(R.id.ticketBag_price_tv)
        var dateTv = itemView?.findViewById<TextView>(R.id.ticketBag_card_date_tv)
        var refundBt = itemView?.findViewById<Button>(R.id.refund_card_bt)
        var refundEt = itemView?.findViewById<EditText>(R.id.ticketBag_card_idNumber_et)
        init {
            refundBt?.setOnClickListener{
                listener.onItemclick(adapterPosition, refundEt?.text.toString())
            }
        }


        fun bindData(ticketBag: TicketBag) {
            val price = "€${ticketBag.price}"
            priceTv?.text = price
            dateTv?.text = ticketBag.mealDate



        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketBagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TicketBagViewHolder(inflater,parent,myListener)
    }

    override fun onBindViewHolder(holder: TicketBagViewHolder, position: Int) {
        val ticketBag = mList[position]
        holder.bindData(ticketBag)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun getitem(position: Int): TicketBag {
        return mList[position]
    }
}