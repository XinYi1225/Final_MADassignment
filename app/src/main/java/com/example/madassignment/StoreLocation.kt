package com.example.madassignment

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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
//            val fragment = MapsFragment()
//            val transition = supportFragmentManager.beginTransaction()
//            transition.replace(R.id.map_fragment_1,fragment)
//            transition.commit()

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

//        val menu = findViewById<ImageView>(R.id.menu_list)
//        menu.setOnClickListener {
//
////            drawerLayout.openDrawer(GravityCompat.START)
//        }
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        // Add a marker in Sydney and move the camera
//        val store_loc1 = LatLng(3.088465, 101.690533)
//
//        val store_loc2 = LatLng(2.996970, 101.673820)
//
//        val zoomLevel = 10f // 1- World, 5- Landmass/continent, 10- City, 15: Streets, 20- Building
//
//
//
//        mMap.addMarker(
//            MarkerOptions().position(store_loc2).title("Mr Framer Grocer (Equine Park)")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//        )
//        mMap.moveCamera(newLatLngZoom(store_loc2, zoomLevel))
//
//
//        mMap.addMarker(
//            MarkerOptions().position(store_loc1).title("Mr Framer Grocer (Kuchai Lama)")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
//        )
//        mMap.moveCamera(newLatLngZoom(store_loc1, zoomLevel))
//
//        with(mMap.uiSettings) {
//            setZoomControlsEnabled(true)
//            setCompassEnabled(true)
//            setScrollGesturesEnabled(true)
//            setTiltGesturesEnabled(true)
//            setRotateGesturesEnabled(true)
//
//            /*isZoomControlsEnabled = isChecked(R.id.zoom_button)
//            isCompassEnabled = isChecked(R.id.compass_button)
//            isMyLocationButtonEnabled = isChecked(R.id.myloc_button)
//            isIndoorLevelPickerEnabled = isChecked(R.id.level_button)
//            isMapToolbarEnabled = isChecked(R.id.maptoolbar_button)
//            isZoomGesturesEnabled = isChecked(R.id.zoomgest_button)
//            isScrollGesturesEnabled = isChecked(R.id.scrollgest_button)
//            isTiltGesturesEnabled = isChecked(R.id.tiltgest_button)
//            isRotateGesturesEnabled = isChecked(R.id.rotategest_button)*/
//
//        }
//
//
//        setUpMap()
//
//
//    }
//    companion object {
//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
//    }
//
//    private fun setUpMap() {
//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//            return
//        }
//    }
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        // Change the map type based on the user's selection.
//        R.id.normal_map -> {
//            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//            true
//        }
//        R.id.hybrid_map -> {
//            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
//            true
//        }
//        R.id.satellite_map -> {
//            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//            true
//        }
//        R.id.terrain_map -> {
//            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.map_option, menu)
//        return true
//    }
//
//}

//    internal lateinit var mLastLocation: Location
//    internal var mCurrLocationMarker: Marker? = null
//    internal var mGoogleApiClient: GoogleApiClient? = null
//    internal lateinit var mLocationRequest: LocationRequest

//    @Synchronized

//     fun onConnected(bundle: Bundle?) {
//
//        mLocationRequest = LocationRequest()
//        mLocationRequest.interval = 1000
//        mLocationRequest.fastestInterval = 1000
//        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//
//    }
//
//     fun onConnectionSuspended(i: Int) {
//
//    }
//
//     fun onLocationChanged(location: Location) {
//
//        mLastLocation = location
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker!!.remove()
//        }
//        //Place current location marker
//        val latLng = LatLng(location.latitude, location.longitude)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Position")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
//
//        //move map camera
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//
//        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.getFusedLocationProviderClient(this)
//        }
//
//    }

//     fun onConnectionFailed(connectionResult: ConnectionResult) {
//
//    }

    /*fun searchLocation(view: View) {
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
    }*/



