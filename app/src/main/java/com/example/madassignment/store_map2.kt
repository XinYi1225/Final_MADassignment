package com.example.madassignment

import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.madassignment.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class store_map2 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        toolbar = findViewById<Toolbar>(R.id.map_toolbar)
        setSupportActionBar(toolbar)

        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        //Up button
        go_back.setOnClickListener {
            // your code to perform when the user clicks on the TextView
            //Toast.makeText(this@MainActivity, "You clicked on TextView 'Click Me'.", Toast.LENGTH_SHORT).show()
          onBackPressed()
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
//        val store_loc1 = LatLng(3.088465, 101.690533)

        val store_loc2 = LatLng(2.996970, 101.673820)

        val zoomLevel = 15f // 1- World, 5- Landmass/continent, 10- City, 15: Streets, 20- Building


        mMap.addMarker(
            MarkerOptions().position(store_loc2).title("Mr Framer Grocer (Equine Park)")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        )
        mMap.moveCamera(newLatLngZoom(store_loc2, zoomLevel))


//        mMap.addMarker(
//            MarkerOptions().position(store_loc1).title("Mr Framer Grocer (Kuchai Lama)")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
//        )
//        mMap.moveCamera(newLatLngZoom(store_loc1, zoomLevel))

        with(mMap.uiSettings) {
            setZoomControlsEnabled(true)
            setCompassEnabled(true)
            setScrollGesturesEnabled(true)
            setTiltGesturesEnabled(true)
            setRotateGesturesEnabled(true)



        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.map_option, menu)

        return true
    }



    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}