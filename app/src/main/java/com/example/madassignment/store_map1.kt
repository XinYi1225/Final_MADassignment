package com.example.madassignment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.madassignment.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar

class store_map1 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var toolbar: Toolbar

//    private val LOCATION_PERMISSION_REQUEST = 1
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback
//
//    private fun getLocationAccess() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED) {
//            mMap.isMyLocationEnabled = true
//            getLocationUpdates()
//            startLocationUpdates()
//        }
//        else
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST
//            )
//    }
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == LOCATION_PERMISSION_REQUEST) {
//            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
//                getLocationAccess()
//            }
//            else {
//                Toast.makeText(
//                    this,
//                    "User has not granted location access permission",
//                    Toast.LENGTH_LONG
//                ).show()
//                finish()
//            }
//        }
//    }
//
//
//    private fun getLocationUpdates() {
//        locationRequest = LocationRequest()
//        locationRequest.interval = 30000
//        locationRequest.fastestInterval = 20000
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                if (locationResult.locations.isNotEmpty()) {
//                    val location = locationResult.lastLocation
//                    if (location != null) {
//                        val latLng = LatLng(location.latitude, location.longitude)
//                        val markerOptions = MarkerOptions().position(latLng)
//                        mMap.addMarker(markerOptions)
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//
//                    }
//                }
//            }
//        }
//    }
////
//    private fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        toolbar = findViewById<Toolbar>(R.id.map_toolbar)
        setSupportActionBar(toolbar)

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        //Up button
        go_back.setOnClickListener {

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
        val store_loc1 = LatLng(3.088465, 101.690533)

//        val store_loc2 = LatLng(2.996970, 101.673820)

        val zoomLevel = 15f // 1- World, 5- Landmass/continent, 10- City, 15: Streets, 20- Building


//        mMap.addMarker(
//            MarkerOptions().position(store_loc2).title("Mr Framer Grocer (Equine Park)")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//        )
//        mMap.moveCamera(newLatLngZoom(store_loc2, zoomLevel))


        mMap.addMarker(
            MarkerOptions().position(store_loc1).title("Mr Framer Grocer (Kuchai Lama)")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        )
        mMap.moveCamera(newLatLngZoom(store_loc1, zoomLevel))

        with(mMap.uiSettings) {
            setZoomControlsEnabled(true)
            setCompassEnabled(true)
            setScrollGesturesEnabled(true)
            setTiltGesturesEnabled(true)
            setRotateGesturesEnabled(true)

            /*isZoomControlsEnabled = isChecked(R.id.zoom_button)
            isCompassEnabled = isChecked(R.id.compass_button)
            isMyLocationButtonEnabled = isChecked(R.id.myloc_button)
            isIndoorLevelPickerEnabled = isChecked(R.id.level_button)
            isMapToolbarEnabled = isChecked(R.id.maptoolbar_button)
            isZoomGesturesEnabled = isChecked(R.id.zoomgest_button)
            isScrollGesturesEnabled = isChecked(R.id.scrollgest_button)
            isTiltGesturesEnabled = isChecked(R.id.tiltgest_button)
            isRotateGesturesEnabled = isChecked(R.id.rotategest_button)*/

        }
//        getLocationAccess()

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







//        override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        // Change the map type based on the user's selection.
//            R.id.normal_map -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//                true
//            }
//            R.id.hybrid_map -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
//                true
//            }
//            R.id.satellite_map -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//                true
//            }
//            R.id.terrain_map -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
//                true
//            }
//        else -> super.onOptionsItemSelected(item)
//    }


}