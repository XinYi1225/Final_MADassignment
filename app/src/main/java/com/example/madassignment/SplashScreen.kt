package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.internal.s
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        Handler().postDelayed({
            // This method will be executed once the timer is over
            //start main activity
//            startActivity(Intent(this@SplashScreen, Login::class.java))
//            finish ()
            //this activity

            val user = auth.currentUser
            if (user != null) {
                // User is signed in
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()

            } else {
                // No user is signed in
                startActivity(Intent(this@SplashScreen, Login::class.java))
                finish()
            }

        }, 1000) //3second splash time

    }
}