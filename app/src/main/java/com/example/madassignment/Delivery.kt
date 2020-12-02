package com.example.madassignment

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.madassignment.R
import com.example.madassignment.deliveryCheckout.CheckoutActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.IOException


class Delivery : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var user: User
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    lateinit var address1_delivery: TextView
    lateinit var town_delivery: TextView
    lateinit var postalCode_delivery: TextView
    lateinit var state_delivery: TextView
    var complete_address: String =""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Up button
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {
            onBackPressed()
        }

        address1_delivery = findViewById(R.id.address1_delivery)
        town_delivery = findViewById(R.id.town_delivery)
        postalCode_delivery = findViewById(R.id.postalCode_delivery)
        state_delivery = findViewById(R.id.state_delivery)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        readAddress()

        val delivery_button = findViewById<Button>(R.id.delivery_button)
        val delivery_radiobutton1 = findViewById<RadioButton>(R.id.delivery_radiobutton1)
        val delivery_radiobutton2 = findViewById<RadioButton>(R.id.delivery_radiobutton2)
        val delivery_radiogroup = findViewById<RadioGroup>(R.id.delivery_radiogroup)

        val delivery_searchAddress = findViewById<EditText>(R.id.editText)

        delivery_button.setOnClickListener() {

            if (delivery_radiogroup.checkedRadioButtonId != -1) {
                if (delivery_radiobutton1.isChecked) {

                    val intent = Intent(this, CheckoutActivity ::class.java)

                    Log.i("complete_address",complete_address)
                    intent.putExtra("data", complete_address)
                    startActivity(intent)
                }

                else if (delivery_radiobutton2.isChecked) {
                    val intent = Intent(this, CheckoutActivity ::class.java)
                    intent.putExtra("data", delivery_searchAddress.text.toString())

                    startActivity(intent)
                }
            }

            else {
                Toast.makeText(applicationContext, "Select Address!", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun readAddress() {
        val database = FirebaseDatabase.getInstance()
//        val auth = FirebaseAuth.getInstance()
        val ref = database.getReference("Profile/" + auth.currentUser!!.uid + "/")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user: User = dataSnapshot.getValue(User::class.java)!!

                address1_delivery.setText(user.Address)
                town_delivery.setText(user.Town)
                postalCode_delivery.setText(user.PostalCode)
                state_delivery.setText(user.State)

                complete_address = user.Address + " "  + user.Town + " "  + user.PostalCode + " "  + user.State

                Log.i("user", user.toString())
                Log.i("address",complete_address)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        });
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Add a marker in store_loc1 and move the camera
        val store_loc1 = LatLng(3.088465, 101.690533)
        val zoomLevel = 15f // 1- World, 5- Landmass/continent, 10- City, 15: Streets, 20- Building
        mMap.addMarker(
            MarkerOptions().position(store_loc1).title("Mr Framer Grocer (Kuchai Lama)")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        mMap.moveCamera(newLatLngZoom(store_loc1,zoomLevel))

        with(mMap.uiSettings) {
            setZoomControlsEnabled(true)
            setCompassEnabled(true)
            setScrollGesturesEnabled(true)
            setTiltGesturesEnabled(true)
            setRotateGesturesEnabled(true)


        }

    }

    fun searchLocation(view: View) {
        val locationSearch: EditText = findViewById<EditText>(R.id.editText)
        lateinit var location: String
        location = locationSearch.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext,"provide location",Toast.LENGTH_SHORT).show()
        }
        else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()
        }
    }
}