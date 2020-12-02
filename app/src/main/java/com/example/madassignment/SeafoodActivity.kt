package com.example.madassignment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.adapter.ItemAdapter
import com.example.madassignment.delivery.HistoryActivity
import com.example.madassignment.object_class.Cart
import com.example.madassignment.object_class.Item
import com.example.madassignment.object_class.ShoppingCart
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.paperdb.Paper

class SeafoodActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener,
    NavigationView.OnNavigationItemSelectedListener{

    private var  myDataset :  MutableList<Item> = mutableListOf()
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var toolbar: Toolbar
    lateinit var adapter: ItemAdapter
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    private var mStorageRef: StorageReference? = null
    lateinit var pushlist: List<Item>
    private val item: Item = Item()
    private var category : String = "Seafood"

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seafood)
        Paper.init(this)
        // Initialize data.
        readData()
        mStorageRef = FirebaseStorage.getInstance().getReference()

        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP;
        flexManager.flexDirection = FlexDirection.ROW;
        flexManager.alignItems = AlignItems.FLEX_START

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = ItemAdapter(this, myDataset, this, myDataset)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = flexManager

        toolbar = findViewById<Toolbar>(R.id.vege_toolbar)
        setSupportActionBar(toolbar)


        val floatingbutton: FloatingActionButton = findViewById(R.id.floating_action_button)
        floatingbutton.setOnClickListener() {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val header_menu : ImageView = findViewById(R.id.nav_menu)
        header_menu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener(this)


    }
    //ON EACH ITEM CLICKED
    override fun onItemClick(position: Int) {

//        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val myIntent = Intent(this, ProdDesc::class.java)

//        Log.i("checkObj", myDataset[position].toString())
        val key = myDataset[position].itemID

        myIntent.putExtra("key", key)
        myIntent.putExtra("category", category)
        startActivity(myIntent)
    }

    override fun addtocart(position: Int) {
        val obj = Cart(
            myDataset[position].itemID,
            myDataset[position].itemName,
            myDataset[position].itemImage,
            myDataset[position].itemPrice,
            1
        )
        ShoppingCart.addItem(obj)
        Toast.makeText(applicationContext, "Successfully Added To Cart", Toast.LENGTH_SHORT).show()
    }

    // filtering / Search Function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.vege, menu)
        var item: MenuItem = menu!!.findItem(R.id.action_search)
        searchView = item.actionView as androidx.appcompat.widget.SearchView
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                toolbar.setBackgroundColor(Color.WHITE)

                return true

            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
//                toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                searchView.setQuery("", false)
                return true
            }


        })

        searchView.maxWidth = Int.MAX_VALUE
        searchName(searchView)
        return true
    }

    private fun searchName(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }

    }
    fun readData(){
        // Write a message to the database

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Item/" + category + "/")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot : DataSnapshot) {

                for ( childDataSnapshot : DataSnapshot in dataSnapshot.getChildren()) {
                    myRef = database.getReference("Item/" + category + "/"+ childDataSnapshot.getKey().toString())
//                    Log.i("data", childDataSnapshot.getKey().toString()); //displays the key for the node

//                    Log.i("data",childDataSnapshot.child(childDataSnapshot.getKey().toString()).value.toString());
                    Log.i("data",childDataSnapshot.getValue(Item::class.java).toString());//gives the value for given keyname
                    childDataSnapshot.getValue(Item::class.java)?.let { myDataset.add(it) }
                    adapter.notifyItemInserted(myDataset.size)

//                        Log.i("data",myDataset.toString())
                }

            }

            override  fun onCancelled(databaseError : DatabaseError) {

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