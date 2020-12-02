package com.example.madassignment


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.madassignment.object_class.Cart

import com.example.madassignment.object_class.Item
import com.example.madassignment.object_class.ShoppingCart
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper


class ProdDesc : AppCompatActivity() {

    lateinit var key: String
    lateinit var category: String
    var item = Item()
    lateinit var itemName: TextView
    lateinit var itemImage: ImageView
    lateinit var stockStatus: TextView
    lateinit var price: TextView
    lateinit var item_weight: TextView
    lateinit var item_cat: TextView
    lateinit var addtoCart: Button
    lateinit var counter: ElegantNumberButton
    override fun onCreate(savedInstanceState: Bundle?) {
        var item: Item = Item();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_desc)


        Paper.init(applicationContext)
        itemName = findViewById(R.id.product_name)
        itemImage = findViewById(R.id.product_image)
        stockStatus = findViewById(R.id.stock_status)
        price = findViewById(R.id.prod_price)
        item_weight = findViewById(R.id.item_weight)
        item_cat = findViewById(R.id.category)
        if (intent.extras != null) {
            key = intent.getStringExtra("key").toString()
            category = intent.getStringExtra("category").toString()
            readItem()
            Log.i("extra", key + category)

//            Glide.with(this).load(item.itemImage).into(itemImage)
        }


         addtoCart = findViewById(R.id.add_to_cart)
         counter = findViewById(R.id.qty_button)


        backTo();


    }


    fun backTo() {
        val back: ImageView = findViewById(R.id.arrow_back_icon)
        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun readItem() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("Item/" + category + "/" + key + "/")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                item = dataSnapshot.getValue(Item::class.java)!!
                itemName.setText(item.itemName)
                price.setText("RM " + String.format("%.2f", item.itemPrice))
                Glide.with(applicationContext).load(item.itemImage).into(itemImage)
                Log.i("checkButton", item.itemImage)
                if (category != "Chicken") {
                    if (item.itemSellQty_kg != 0.0) {
                        Log.i(
                            "qty",
                            item.itemSellQty_kg.compareTo(item.itemQtyRemain_kg_or_pcs).toString()
                        )
                        if (item.itemSellQty_kg.compareTo(item.itemQtyRemain_kg_or_pcs) < 0) {

                            stockStatus.text = this@ProdDesc.getString(R.string.haveStock)
                        } else {

                            stockStatus.text = this@ProdDesc.getString(R.string.outOfStock)
                        }
                    }
                } else {
                    if (item.itemQtyRemain_kg_or_pcs == 0.0) {
                        stockStatus.text = this@ProdDesc.getString(R.string.outOfStock)
                    } else {
                        stockStatus.text = this@ProdDesc.getString(R.string.haveStock)
                    }
                }
                if (item.itemSellQty_kg > 0.0) {
                    item_weight.setText(
                        item_weight.text.toString() + String.format(
                            "%.2fkg",
                            item.itemSellQty_kg
                        )
                    )
                } else {
                    item_weight.setText("Weight: N/A")
                }
                item_cat.setText(item_cat.text.toString() + category)
                addtoCart.setOnClickListener() {
                    Log.i("checkButton", item.toString())
                    var cartItem: Cart = Cart(
                        itemID = item.itemID,
                        itemName = item.itemName,
                        itemImage = item.itemImage,
                        itemPrice = item.itemPrice,
                        itemQty = counter.number.toInt()
                    )

                    ShoppingCart.addItem(cartItem)
                    Log.i("checkButton", ShoppingCart.getShoppingCartSize().toString())
                    Toast.makeText(applicationContext, "Successfully Added To Cart", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        });
    }


}

