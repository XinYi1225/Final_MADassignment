package com.example.madassignment.delivery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madassignment.R


internal class PurchaseAdapter(private var purchaseList: List<DetailModel>,  private var context: Context
) :
    RecyclerView.Adapter<PurchaseAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var item_pic: ImageView = view.findViewById(R.id.item_pic)
        var item_name: TextView = view.findViewById(R.id.item_name)
        var item_qty: TextView = view.findViewById(R.id.quantity)
        var item_price: TextView = view.findViewById(R.id.item_price)

//        var transaction_date: TextView = view.findViewById(R.id.transaction_date)
//        val list: MutableList<DetailModel> = mutableListOf()
//        var total_item: TextView = view.findViewById(R.id.total_item)
//        var paymentMethod: TextView = view.findViewById(R.id.payment_method)
//        var status: TextView = view.findViewById(R.id.status)
//        var subtotal: TextView = view.findViewById(R.id.subtotal_amt)
//        var shipping: TextView = view.findViewById(R.id.shipping_amt)
//        var total_order_amt: TextView = view.findViewById(R.id.total_order_amt)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_purchase_layout, parent, false)
        return MyViewHolder(itemView)
    }

    //hold data
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val purchase = purchaseList[position]
        Log.i("adapter",purchase.toString() + " " + purchaseList)
       Glide.with(context).load(purchase.item_pic).into(holder.item_pic)
        holder.item_name.text = purchase.item_name
        holder.item_qty.text = purchase.item_qty.toString()
        holder.item_price.text = String.format("%.2f",purchase.item_price)
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }
}