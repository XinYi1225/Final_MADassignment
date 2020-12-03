package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.madassignment.delivery.HistoryActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class View_Profile : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var clickable_logout: TextView
    lateinit var clickable_edit: ImageView

    lateinit var firstName: TextView
    lateinit var lastName: TextView
    lateinit var gender: TextView
    lateinit var phoneNo: TextView
    lateinit var email: TextView
    lateinit var dob: TextView
    lateinit var address1: TextView
    lateinit var town: TextView
    lateinit var postalCode: TextView
    lateinit var state: TextView

    private val tag = "View_Profile"
    private lateinit var mUser: User
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP;
        flexManager.flexDirection = FlexDirection.ROW;
        flexManager.alignItems = AlignItems.CENTER

        clickable_logout = findViewById(R.id.logout_text)
//        readItem()
        clickable_logout.setOnClickListener {

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

        clickable_edit = findViewById<ImageView>(R.id.edit_icon)

        clickable_edit.setOnClickListener {
            val intent = Intent(this, Edit_profile::class.java)
            startActivity(intent)
        }


        firstName = findViewById(R.id.firstName_text)
        lastName = findViewById(R.id.lastName_text)
        gender = findViewById(R.id.gender_text)
        phoneNo = findViewById(R.id.phoneNo_text)
        email = findViewById(R.id.email_text)
        dob = findViewById(R.id.DOB_text)
        address1 = findViewById(R.id.address1_text)
        town = findViewById(R.id.town_text)
        postalCode = findViewById(R.id.postalCode_text)
        state = findViewById(R.id.state_text)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        readItem()

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        val header_menu : ImageView = findViewById(R.id.nav_menu_header)
        header_menu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener(this)


    }

    fun readItem(){
        val database = FirebaseDatabase.getInstance()
//        val auth = FirebaseAuth.getInstance()
        val ref = database.getReference("Profile/" + auth.currentUser!!.uid + "/")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user: User = dataSnapshot.getValue(User::class.java)!!

                firstName.setText(user.FirstName)
                lastName.setText(user.LastName)
                gender.setText(user.Gender)
                phoneNo.setText(user.PhoneNumber)
                email.setText(user.Email)
                gender.setText(user.Gender)
                dob.setText(user.DateofBirth)
                address1.setText(user.Address)
                town.setText(user.Town)
                postalCode.setText(user.PostalCode)
                state.setText(user.State)

                Log.i("user", user.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        });
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
                auth.signOut()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true



    }
}