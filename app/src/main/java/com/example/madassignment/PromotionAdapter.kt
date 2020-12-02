package com.example.madassignment

import android.content.Context
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madassignment.PromotionModel

internal class PromotionAdapter(private var promotionList: List<PromotionModel>,
                                private var listener: OnItemClickListener,
                                private var context: Context
) :
    RecyclerView.Adapter<PromotionAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var promtion_image: ImageView = view.findViewById(R.id.promotion_image)
        var promotion_title: TextView = view.findViewById(R.id.promotion_title)
        var promotion_subtitle: TextView = view.findViewById(R.id.promotion_subtitle)
        var promotion_price: TextView = view.findViewById(R.id.promotion_price)

        var promtion_button: Button = view.findViewById(R.id.promotion_btn)

        init {
            promtion_button.setOnClickListener(this)
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
            .inflate(R.layout.promotion_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val promotion = promotionList[position]

        Glide.with(context).load(promotion.getImage()).into(holder.promtion_image)

//        holder.promtion_image.setImageResource(promotion.getImage())
        holder.promotion_title.text = promotion.getTitle()
        holder.promotion_subtitle.text = promotion.getSubTitle()
        holder.promotion_price.text = "RM " + String.format("%.2f",promotion.getPrice())


    }
    override fun getItemCount(): Int {
        return promotionList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}