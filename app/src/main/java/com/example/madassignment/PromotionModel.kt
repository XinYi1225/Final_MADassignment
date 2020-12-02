package com.example.madassignment

import android.widget.ImageView
import java.io.Serializable

class PromotionModel:Serializable{

    private var promotion_id: String = ""
    private var promotion_title: String = ""
    private var promotion_subtitle: String = ""
    private var promotion_price: Double = 0.0
    private var promtion_image: String = ""

    constructor(promotion_id: String,
                promtion_image: String,
                promotion_title: String?,
                promotion_subtitle: String?,
                promotion_price: Double)
    {
        this.promotion_id = promotion_id
        this.promtion_image = promtion_image
        this.promotion_title = promotion_title!!
        this.promotion_subtitle = promotion_subtitle!!
        this.promotion_price = promotion_price

    }

    constructor(){}

//    init {
//        this.promtion_image = promtion_image
//        this.promotion_title = promotion_title!!
//        this.promotion_subtitle = promotion_subtitle!!
//        this.promotion_price = promotion_price!!
//
//    }
    fun getID(): String {
     return promotion_id
    }
    fun setID(name: String) {
        promotion_id = name
    }
    fun getImage(): String {
        return promtion_image
    }
    fun setImage(name: String) {
        promtion_image = name
    }

    fun getTitle(): String? {
        return promotion_title
    }
    fun setTitle(title: String?) {
        promotion_title = title!!
    }
    fun getSubTitle(): String? {
        return promotion_subtitle
    }
    fun setSubTitle(subtitle: String?) {
        this.promotion_subtitle = subtitle!!
    }
    fun getPrice(): Double {
        return promotion_price
    }
    fun setPrice(price: Double) {
        this.promotion_price = price!!
    }


}