package com.example.madassignment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madassignment.object_class.Cart
import com.example.madassignment.R
import com.example.madassignment.ButtonClickedListener


class CartAdapter(
    // variable
        private var cartList: List<Cart>,
        private var context: Context,
        private val buttonclick : ButtonClickedListener,
    ) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    var isSelectedAll: Boolean = false


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.cart_item_image)
        val title: TextView = view.findViewById(R.id.cart_item_name)
        val price: TextView = view.findViewById(R.id.cart_item_price)
        val qty: TextView = view.findViewById(R.id.cart_item_qty)
        val checkbox: CheckBox = view.findViewById(R.id.checkbox_in_list)
        val addQty: ImageButton = view.findViewById(R.id.add_qty)
        val minusQty: ImageButton = view.findViewById(R.id.minus_qty)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list, parent, false)


        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = cartList[position]
        Glide.with(context).load(item.itemImage).into(holder.imageView)
        holder.price.setText("RM " + String.format("%.2f", item.itemPrice))
        holder.qty.setText(item.itemQty.toString())
        holder.title.setText(item.itemName)
        if (!isSelectedAll) holder.checkbox.setChecked(false);
        else holder.checkbox.setChecked(true);

        holder.addQty.setOnClickListener{
            buttonclick.add(item, position)
        }
        holder.minusQty.setOnClickListener{
            buttonclick.minus(item,position)
        }
        holder.checkbox.setOnClickListener{
            if(holder.checkbox.isChecked){
                buttonclick.getCheckedItem(item,true, position)
                Log.i("cartadapter", position.toString())
            }else{
                buttonclick.getCheckedItem(item,false, position)
            }

        }



    }


    fun selectAll() {
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    fun unselectAll() {
        isSelectedAll = false;
        notifyDataSetChanged();
    }


    override fun getItemCount() = cartList.size


}
