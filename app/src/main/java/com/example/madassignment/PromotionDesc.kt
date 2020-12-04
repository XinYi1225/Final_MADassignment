package com.example.madassignment

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.madassignment.PromotionModel
import com.example.madassignment.object_class.Cart
import com.example.madassignment.object_class.Item
import com.example.madassignment.object_class.ShoppingCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PromotionDesc : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase? =null
    var item: PromotionModel = PromotionModel()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion_desc)

        var promtion_image: ImageView = findViewById(R.id.promotion_image)
        var promotion_title: TextView = findViewById(R.id.cardview_title)
        var promotion_subTitle: TextView = findViewById(R.id.discount_text_image)
        var promotion_price: TextView = findViewById(R.id.cardview_price)

        if (intent.extras != null) {
            item = intent.getSerializableExtra("Item") as PromotionModel

            Log.i("checkObj", intent.extras.toString())

            Glide.with(this).load(item.getImage()).into(promtion_image)
            promotion_title.setText(item.getTitle())
            promotion_subTitle.setText(item.getSubTitle())
            promotion_price.text = "RM " + String.format("%.2f",item.getPrice())

//            price.setText("RM " + String.format("%.2f", item.getItemPrice()))


        }
        val counter = findViewById<ElegantNumberButton>(R.id.cardview_qty_btn)

        val addtoCart: Button = findViewById(R.id.addtocart)

        addtoCart.setOnClickListener() {
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
            Log.i("checkButton", this.applicationContext.toString())

            var obj = Cart(
                item.getID(),
                item.getTitle().toString(),
                item.getImage(),
                item.getPrice(),
                counter.number.toInt(),
            )



            ShoppingCart.addItem(obj)

        }


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Promotion")


        goback()
    }

    fun goback() {
        val back: ImageView = findViewById(R.id.arrow_back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}