package com.example.madassignment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.madassignment.deliveryCheckout.CheckoutActivity
import com.example.madassignment.pickupCheckout.PickupActivity
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.io.IOException

class PickUp : AppCompatActivity(),OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    lateinit var mapView: MapView

    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"

    private val DEFAULT_ZOOM = 15f

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    lateinit var tvCurrentAddress: TextView

    var end_latitude = 0.0
    var end_longitude = 0.0
    var origin:MarkerOptions?=null
    var destination:MarkerOptions?=null
    var latitude = 0.0
    var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_up)

        mapView = findViewById<MapView>(R.id.map1)

        tvCurrentAddress = findViewById<TextView>(R.id.tvAdd)

        var mapViewBundle: Bundle? = null
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle((MAP_VIEW_BUNDLE_KEY))
        }

        mapView = findViewById(R.id.map1)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        val store1_kuchai_lama = findViewById<RadioButton>(R.id.store1_kuchai_lama)
        val store2_equine_park = findViewById<RadioButton>(R.id.store2_equine_park)

        store1_kuchai_lama.setOnClickListener{
            searchArea("No. 28, Jalan 2/116B, Kuchai Entrepreneurs Park, 58200 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
        }

        store2_equine_park.setOnClickListener{
            searchArea("21, Jalan Equine 9b, Taman Equine, 43300 Seri Kembangan, Selangor")
        }

        //Up button
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {
            onBackPressed()
        }

        val pickup_button = findViewById<Button>(R.id.pickup_button)
        val pickup_radiogroup = findViewById<RadioGroup>(R.id.pickup_radiogroup)
        val store1 ="No.28, Jalan 2/116B, Kuchai Entreprenuers Park, Off Kuchai Lama, 58200 Kuala Lumpur"
        val store2 = "21, Jalan Equine 9b, Taman Equine, 43300 Seri Kembangan, Selangor"

        pickup_button.setOnClickListener() {

            if (pickup_radiogroup.checkedRadioButtonId != -1) {
                if (store1_kuchai_lama.isChecked) {
                    val intent = Intent(this, PickupActivity ::class.java)
                    intent.putExtra("pickup_address", store1)
                    startActivity(intent)
                }

                else if (store2_equine_park.isChecked) {
                    val intent = Intent(this, PickupActivity ::class.java)
                    intent.putExtra("pickup_address", store2)

                    startActivity(intent)
                }
            }

            else {
                Toast.makeText(applicationContext, "Select Store!", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun searchArea(location:String) {

        var addressList: List<Address>? = null
        val markerOptions = MarkerOptions()

        Log.d("location = ",location)
        if(location!=""){
            val geocoder = Geocoder(applicationContext)
            try{
                addressList = geocoder.getFromLocationName(location,5)
            }catch (e:IOException){
                e.printStackTrace()
            }
            if(addressList!=null){
                for(i in addressList.indices){
                    val myAddress = addressList[i]
                    val latLng=
                            LatLng(myAddress.latitude,myAddress.longitude)
                    markerOptions.position(latLng)
                    mMap!!.addMarker(markerOptions)
                    end_latitude = myAddress.latitude
                    end_longitude = myAddress.longitude
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    val mo = MarkerOptions()
                    mo.title("Distance")

                    val results = FloatArray(10)
                    Location.distanceBetween(
                            latitude,
                            longitude,
                            end_latitude,
                            end_longitude,results
                    )
                    val s= String.format("%.2f",results[0]/1000)

                    //Setting marker to draw the route between these 2 points
                    origin = MarkerOptions().position(LatLng(latitude,longitude))
                            .title("HSR Layout").snippet("origin")

                    destination = MarkerOptions().position(LatLng(end_latitude,end_longitude))
                            .title(location)
                            .snippet("Distance = $s KM")

                    mMap!!.addMarker(destination)

                    Toast.makeText(this@PickUp,"Distance = $s KM",Toast.LENGTH_SHORT).show()

                    tvCurrentAddress!!.setText("Distance = $s KM")

                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mapView.onResume()
        mMap = googleMap

        askGalleryPermissionLocation()

        if(ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED){
            return
        }
        mMap!!.setMyLocationEnabled(true)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        askGalleryPermissionLocation()

        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if(mapViewBundle==null){
            mapViewBundle= Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY,mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    private fun askGalleryPermissionLocation() {
        askPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ){
            getCurrentLocation()
        }.onDeclined{e->
            if(e.hasDenied()){
                //the list of denied permissions
                e.denied.forEach{}
            }

            AlertDialog.Builder(this)
                    .setMessage("Please accept our permissions.. Otherwise you will not be able to use some of our important features")
                    .setPositiveButton("Yes"){_,_->
                        e.askAgain()
                    }//ask again
                    .setNegativeButton("no"){dialog,_->
                        dialog.dismiss()
                    }
                    .show()


            if(e.hasForeverDenied()){
                //the lsit of forever denied permissions, user has check "never ask again"
                e.foreverDenied.forEach{

                }
                //you need to open setting manually if you really need it
                e.goToSettings();
            }
        }
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this@PickUp)

        try{
            @SuppressLint("MissingPermission")
            val location =
                    fusedLocationProviderClient!!.lastLocation

            location.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(loc: Task<Location>){
                    if(loc.isSuccessful){

                        val currentLocation = loc.result as Location?
                        if(currentLocation!=null){
                            moveCamera(
                                    LatLng(currentLocation.latitude,currentLocation.longitude),
                                    DEFAULT_ZOOM
                            )

                            latitude = currentLocation.latitude
                            longitude = currentLocation.longitude
                        }
                    } else{

                        askGalleryPermissionLocation()
                    }
                }
            })
        }catch(se: Exception){
            Log.e("TAG","Security Exception")
        }
    }

    private fun moveCamera(latLng: LatLng,zoom: Float){
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))
    }

}

