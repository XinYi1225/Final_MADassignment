package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.madassignment.PickUp

class DeliveryOrPickup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_or_pickup)

        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)

        //Up button
        go_back.setOnClickListener {
            onBackPressed()
        }

        val delivery_button = findViewById<Button>(R.id.delivery_button)

        delivery_button.setOnClickListener{
            val intent = Intent(this,Delivery::class.java)
            startActivity(intent)
        }

        val pickup_button = findViewById<Button>(R.id.pickup_button)

        pickup_button.setOnClickListener{
            val intent = Intent(this,PickUp::class.java)
            startActivity(intent)
        }
    }
}