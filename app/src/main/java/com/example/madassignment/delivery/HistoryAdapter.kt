package com.example.madassignment.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.R
import com.example.madassignment.delivery.HistoryModel


internal class HistoryAdapter(private var historyList: List<HistoryModel>, private var listener: OnItemClickListener,
) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var transaction_date: TextView = view.findViewById(R.id.transaction_date)
        var item_name: TextView = view.findViewById(R.id.item_name)
        var item_price: TextView = view.findViewById(R.id.item_price)
        var item_qty: TextView = view.findViewById(R.id.quantity)
        var total_item: TextView = view.findViewById(R.id.total_item)
        var total_order_amt: TextView = view.findViewById(R.id.total_order_amt)

        var clickableViewMore = view.findViewById<TextView>(R.id.view_more)

        init{
            clickableViewMore.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.delivery_history_layout, parent, false)
        return MyViewHolder(itemView)
    }

    //hold data
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        historyList = historyList.sortedByDescending { it.transaction_date }
        val history = historyList[position]

        holder.transaction_date.text = history.transaction_date
        holder.item_name.text = history.item_list[0].item_name
        holder.item_qty.text = history.item_list[0].item_qty.toString()
        holder.item_price.text = String.format("%.2f",history.item_list[0].item_price)
        holder.total_item.text = history.total_item.toString()
        holder.total_order_amt.text = String.format("%.2f",history.total_amt)

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}