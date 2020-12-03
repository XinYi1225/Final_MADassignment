package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentDoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_done)

        val payment_done_button = findViewById<Button>(R.id.payment_done_button)

        payment_done_button.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

            
        }

    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Disabled Back Press", Toast.LENGTH_SHORT).show()
    }
}

