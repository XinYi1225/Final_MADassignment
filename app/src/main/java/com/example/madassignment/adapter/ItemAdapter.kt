package com.example.madassignment.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madassignment.R
import com.example.madassignment.object_class.Item


class ItemAdapter(
    private var context: Context,
    private var dataset: MutableList<Item>,
    private val listener: OnItemClickListener,
    private var listFiltered: MutableList<Item>


) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), Filterable {


    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val title: TextView = view.findViewById(R.id.item_title)
        val stockStatusIcon: ImageView = view.findViewById(R.id.stock_status_icon)
        val stockStatus: TextView = view.findViewById(R.id.stockStatus)
        val itemprice: TextView = view.findViewById(R.id.item_price)
        val addtocartBtn: Button = view.findViewById(R.id.addToCartButton)
        val weight: TextView = view.findViewById(R.id.converted_kg)


        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun addtocart(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        val metrics = DisplayMetrics()
        context.display?.getRealMetrics(metrics)
        val devicewidth: Int = metrics.widthPixels
        val deviceheight: Int = metrics.heightPixels
        adapterLayout.layoutParams.width = (devicewidth / 2) - 16

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listFiltered[position]
        holder.title.text = item.itemName
        Glide.with(context).load(item.itemImage).into(holder.imageView)
        if (item.itemSellQty_kg != 0.0) {
            Log.i("qty", item.itemSellQty_kg.compareTo(item.itemQtyRemain_kg_or_pcs).toString())
            if (item.itemSellQty_kg.compareTo(item.itemQtyRemain_kg_or_pcs) < 0) {
                holder.stockStatusIcon.setImageResource(R.drawable.instock)
                holder.stockStatus.text = context.getString(R.string.haveStock)
            } else {
                holder.stockStatusIcon.setImageResource(R.drawable.nostock)
                holder.stockStatus.text = context.getString(R.string.outOfStock)
            }
        } else if (item.itemQtyRemain_kg_or_pcs > 0.0) {
            holder.stockStatusIcon.setImageResource(R.drawable.instock)
            holder.stockStatus.text = context.getString(R.string.haveStock)
        } else {
            holder.stockStatusIcon.setImageResource(R.drawable.nostock)
            holder.stockStatus.text = context.getString(R.string.outOfStock)
        }

//        holder.stockStatusIcon.setImageResource(item.getStockStatusImage())
//        holder.stockStatus.text = context.resources.getString(item.getStockStatus())
        holder.weight.text = String.format("%.2f", item.itemSellQty_kg) + " kg"
        holder.itemprice.text = "RM " + String.format("%.2f", item.itemPrice)

        val metrics = DisplayMetrics()
        context.display?.getRealMetrics(metrics)
        val devicewidth: Int = metrics.widthPixels
        val deviceheight: Int = metrics.heightPixels
        holder.imageView.layoutParams.height = (deviceheight / 4) - 16
        holder.addtocartBtn.setOnClickListener {
            listener.addtocart(position)
        }

    }

    override fun getItemCount() = listFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charString: String = constraint.toString()
                if (charString.isEmpty()) {
                    listFiltered = dataset

                } else {
                    var filteredList: MutableList<Item> = mutableListOf()
                    for (vege: Item in dataset) {

                        if (vege.itemName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(vege)
                            Log.i("hi", vege.itemName.toString())
                        }
                    }
                    listFiltered = filteredList
                }
                var filterResults: FilterResults = FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFiltered = results!!.values as MutableList<Item>
                notifyDataSetChanged()
            }

        }
    }
}