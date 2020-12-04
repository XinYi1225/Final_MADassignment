package com.example.madassignment

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.madassignment.R
import com.example.madassignment.delivery.HistoryActivity
import com.example.madassignment.store_map1
import com.example.madassignment.store_map2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class StoreLocation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mMap: GoogleMap


    //Declaring the needed Variables
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST = 1

    lateinit var expandBtn_1: Button
    lateinit var expandBtn_2: Button
    lateinit var button_1: Button
    lateinit var button_2: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_location)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        expandBtn_1 = findViewById<Button>(R.id.expandBtn_1)
        expandBtn_1.setOnClickListener {

                val expandableLayout_1 = findViewById<LinearLayout>(R.id.expandableLayout_store1)
                val cardView_1 = findViewById<CardView>(R.id.cardView_store1)
                if (expandableLayout_1.visibility == View.GONE) {

                    TransitionManager.beginDelayedTransition(cardView_1, AutoTransition())
                    expandableLayout_1.visibility = View.VISIBLE
                    expandBtn_1.text = "Collapse"

                } else {

                    TransitionManager.beginDelayedTransition(cardView_1, AutoTransition())
                    expandableLayout_1.visibility = View.GONE
                    expandBtn_1.text = "More Details"
                }
            }


        expandBtn_2 = findViewById<Button>(R.id.expandBtn_2)
        expandBtn_2.setOnClickListener {
            val expandableLayout_2 = findViewById<LinearLayout>(R.id.expandableLayout_store2)
            val cardView_2 = findViewById<CardView>(R.id.cardView_store2)
            if (expandableLayout_2.visibility == View.GONE) {

                TransitionManager.beginDelayedTransition(cardView_2, AutoTransition())
                expandableLayout_2.visibility = View.VISIBLE
                expandBtn_2.text = "Collapse"
            } else {
                TransitionManager.beginDelayedTransition(cardView_2, AutoTransition())
                expandableLayout_2.visibility = View.GONE
                expandBtn_2.text = "More Details"
            }
        }

        button_1 = findViewById<Button>(R.id.map_1)
        button_1.setOnClickListener {

            val intent = Intent(this, store_map1::class.java)
            startActivity(intent)
        }


        button_2 = findViewById<Button>(R.id.map_2)
        button_2.setOnClickListener {

            val intent = Intent(this, store_map2::class.java)
            startActivity(intent)
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        val menuList: ImageView = findViewById(R.id.menu_list)
        menuList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.


        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_vegetables -> {
                val intent = Intent(this, Vegetables::class.java)
                startActivity(intent)
            }
            R.id.nav_fruits -> {
                val intent = Intent(this, Fruit::class.java)
                startActivity(intent)
            }
            R.id.nav_seafoods -> {
                val intent = Intent(this, SeafoodActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_chickens -> {
                val intent = Intent(this, Chicken::class.java)
                startActivity(intent)
            }
            R.id.nav_eggs -> {
                val intent = Intent(this, Egg::class.java)
                startActivity(intent)
            }
            R.id.nav_myCart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)

            }
            R.id.nav_account -> {
                val myintent = Intent(this, View_Profile::class.java)
                startActivity(myintent)

            }
            R.id.nav_orderHistory -> {
                val myintent = Intent(this, HistoryActivity::class.java)
                startActivity(myintent)
            }
            R.id.nav_store -> {
                val myintent = Intent(this, StoreLocation::class.java)
                startActivity(myintent)
            }
            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                //set message for alert dialog
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(android.R.drawable.ic_lock_lock)

                //performing positive action
                builder.setPositiveButton("Yes")
                { dialogInterface, which ->
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()

                    auth.signOut()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }

                //performing negative action
                builder.setNegativeButton("No")
                { dialogInterface, which ->
                    Toast.makeText(applicationContext, "Clicked No", Toast.LENGTH_SHORT).show()
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()

                // Set other dialog properties
                alertDialog.show()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true



    }


}


