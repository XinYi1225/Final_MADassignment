package com.example.madassignment.delivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.*
import com.example.madassignment.R
import com.example.madassignment.delivery.DeliveryViewModel
import com.example.madassignment.delivery.HistoryModel
import com.example.madassignment.myPurchase.MyPurchaseActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.libraries.places.internal.it
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*



class HistoryActivity  : AppCompatActivity() , HistoryAdapter.OnItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: DeliveryViewModel

    private val historyList: MutableList<HistoryModel> = mutableListOf()
    private var purchaseList = HistoryModel()
    var key = ""

    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        auth = FirebaseAuth.getInstance()


        Log.i("DeliveryHistoryFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(DeliveryViewModel::class.java)

        val flexManager = FlexboxLayoutManager(applicationContext)
        flexManager.flexWrap = FlexWrap.WRAP;
        flexManager.flexDirection = FlexDirection.ROW;
        flexManager.alignItems = AlignItems.FLEX_START

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        historyAdapter = HistoryAdapter(historyList, this)

        recyclerView.layoutManager = flexManager
        recyclerView.adapter = historyAdapter

        getHistoryData()
//        setCheckoutData()

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val header_menu : ImageView = findViewById(R.id.menu_list)
        header_menu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener(this)

    }


    fun getHistoryData(){

        //Write a message to the database
        val database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Profile/" + auth.currentUser!!.uid +  "/OrderHistory/")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot : DataSnapshot) {

                for ( childDataSnapshot : DataSnapshot in dataSnapshot.getChildren()) {
                    myRef = database.getReference("Profile/"+ auth.currentUser!!.uid + "/OrderHistory/"+ childDataSnapshot.getKey().toString() + "/")
                    childDataSnapshot.getValue(HistoryModel::class.java)?.let {historyList.add(it) }
                    historyAdapter.notifyItemInserted(historyList.size)
                    Log.i("data",historyList.size.toString())
                }

            }

            override  fun onCancelled(databaseError : DatabaseError) {

            }
        });

        //get item list
        var item = HistoryModel()

        var itemRef = database.getReference("Profile/" + auth.currentUser!!.uid + "/OrderHistory/" + key + "/")

        itemRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) = if (snapshot.exists()) {
                item = snapshot.getValue(HistoryModel::class.java)!!
                for (i in item.item_list) {
                    purchaseList.item_list.add(i)
                    historyAdapter.notifyItemInserted(purchaseList.item_list.size)
                }
            } else {
                   Toast.makeText(applicationContext,"Opps, You do not have any oreder yet !",Toast.LENGTH_LONG).show()
//                    val intent = Intent(applicationContext, Empty_History::class.java)
//                    startActivity(intent)


            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("cancel","noOrderHistoryFile")
            }

        });
    }


    override fun onItemClick(position: Int) {
        val myIntent = Intent(this, MyPurchaseActivity::class.java)

        Log.i("checkObj", historyList[position].toString())
        val obj = HistoryModel(

//                historyList[position].order_id,
//                historyList[position].transaction_date,
//                historyList[position].item_list,
//                historyList[position].qty,
//                historyList[position].total_item,
//                historyList[position].subtotal_amt,
//                historyList[position].shipping_fees,
//                historyList[position].total_amt,
//                historyList[position].payment_method,
//                historyList[position].status

        )

        Log.i("checkObj", obj.toString())

        myIntent.putExtra("Item", historyList[position].order_id)
        startActivity(myIntent)
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

