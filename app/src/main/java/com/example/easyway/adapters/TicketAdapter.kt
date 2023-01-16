package com.example.easyway.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Models.Ticket
import com.example.easyway.R



    class TicketAdapter(private var mList : List<Ticket>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
        private lateinit var myListener: onItemClickListener
        public interface onItemClickListener{

            fun onItemclick(position: Int){

            }

        }

        fun setOnItemClickListener(listener: onItemClickListener){
            myListener = listener
        }

        class TicketViewHolder(inflater: LayoutInflater, val parent: ViewGroup,listener: onItemClickListener) : RecyclerView.ViewHolder(inflater.inflate(
            R.layout.ticket_card, parent, false)){
            var priceTv = itemView?.findViewById<TextView>(R.id.ticket_price_tv)
            var dishDescTv = itemView?.findViewById<TextView>(R.id.ticket_card_title_tv)
            var dateTv = itemView?.findViewById<TextView>(R.id.ticket_card_date_tv)
            var cancelBt = itemView?.findViewById<Button>(R.id.cancel_card_bt)

            init {
                cancelBt?.setOnClickListener{
                    listener.onItemclick(adapterPosition)
                }
            }


            fun bindData(ticket: Ticket) {
                val price = "€${ticket.price}"
                priceTv?.text = price
                dishDescTv?.text = ticket.description
                dateTv?.text = ticket.mealDate



            }
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return TicketViewHolder(inflater,parent,myListener)
        }

        override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
            val ticket = mList[position]
            holder.bindData(ticket)
        }

        override fun getItemCount(): Int {
            return mList.count()
        }

        fun getitem(position: Int):Ticket{
            return mList[position]
        }
    }
