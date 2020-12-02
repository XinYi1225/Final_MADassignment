package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.delivery.HistoryActivity
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.paperdb.Paper
import java.util.*

class MainActivity : AppCompatActivity(), PromotionAdapter.OnItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private var promotionList = ArrayList<PromotionModel>()

   // private var  promotionList :  MutableList<PromotionModel> = mutableListOf()

    private lateinit var promotionAdapter: PromotionAdapter
    lateinit var toolbar: Toolbar

    lateinit var vegetables: CardView
    lateinit var fruits: CardView
    lateinit var seafoods: CardView
    lateinit var chickens: CardView
    lateinit var eggs: CardView

    lateinit var username : TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        title = "KotlinApp"
        Paper.init(this)

        toolbar = findViewById<Toolbar>(R.id.home_toolbar)
        setSupportActionBar(toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        promotionAdapter = PromotionAdapter(promotionList, this,this)
//        val mLayoutManager = ConstraintLayout(applicationContext)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = promotionAdapter


//        preparePromotionData()

        vegetables = findViewById<CardView>(R.id.cat_vegetables)
        fruits = findViewById<CardView>(R.id.cat_fruits)
        seafoods = findViewById<CardView>(R.id.cat_seafoods)
        chickens = findViewById<CardView>(R.id.cat_chicken)
        eggs = findViewById<CardView>(R.id.cat_eggs)

//
//        val nav_header =
//            LayoutInflater.from(this).inflate(R.layout.nav_header_bar, null)
//
//     //   username = nav_header.findViewById<TextView>(R.id.profile_name_nav)


        vegetables.setOnClickListener() {
            Toast.makeText(this, "Select Vegetables Category", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Vegetables::class.java)
            startActivity(intent)
        }

        fruits.setOnClickListener() {
            Toast.makeText(this, "Select Fruits Category", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Fruit::class.java)
            startActivity(intent)
        }

        seafoods.setOnClickListener() {
            Toast.makeText(this, "Select Seafoods Category", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SeafoodActivity::class.java)
            startActivity(intent)
        }

        chickens.setOnClickListener() {
            Toast.makeText(this, "Select Chickens Category", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Chicken::class.java)
            startActivity(intent)
        }

        eggs.setOnClickListener() {
            Toast.makeText(this, "Select Eggs Category", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Egg::class.java)
            startActivity(intent)
        }



        val addtocart_fab: FloatingActionButton = findViewById(R.id.fab)
        addtocart_fab.setOnClickListener() {
            Toast.makeText(this, "Add to cart", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = Navigation.findNavController(this,R.id.fragment)
//       // val navController: NavigationView.findNavController(this,nav_view)
//
//        NavigationUI.setupWithNavController(navView,navController)
//        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)


        val menuList: ImageView = findViewById(R.id.nav_menu)
        menuList.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        navView.setNavigationItemSelectedListener(this)

        auth = FirebaseAuth.getInstance()


      //  uploaditemandimage()

        readData()
//      readItem()


    }




    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item  clicked", Toast.LENGTH_SHORT).show()
        val myIntent = Intent(this, PromotionDesc::class.java)

        Log.i("checkObj", promotionList[position].toString())
        val obj = PromotionModel(

            promotionList[position].getID(),
            promotionList[position].getImage(),
            promotionList[position].getTitle(),
            promotionList[position].getSubTitle(),
            promotionList[position].getPrice()

        )

        Log.i("checkObj", obj.toString())

        myIntent.putExtra("Item", obj)
        startActivity(myIntent)

    }

    override fun onBackPressed() {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

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

//    fun uploaditemandimage() {
//
//
////        val file = Uri.fromFile(File("path/to/images/rivers.jpg"))
////        val riversRef: StorageReference? = mStorageRef?.child("images/rivers.jpg")
////        riversRef?.putFile(file)?.addOnSuccessListener { taskSnapshot ->
////            val downloadUrl: Task<Uri> = taskSnapshot.metadata?.reference?.downloadUrl as Task<Uri>
////            downloadUrl.addOnSuccessListener { uri -> // Get a URL to the uploaded content
////                val photolink = uri.toString()
////
////            }
////                .addOnFailureListener {
////                    // Handle unsuccessful uploads
////                    // ...
////                }
////        }
//        val database = FirebaseDatabase.getInstance()
//        val myRef: DatabaseReference = database.getReference("Promotion/")
//
//        val promotionList = ArrayList<PromotionModel>()
//
//    var promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fvegetables%2FCauliflower%20?alt=media&token=6c94bc8d-f3b2-4b67-8ba5-35cba1aca7af", "Hello", "Free Orange", 10.90
//    )
//    promotionList.add(promotion)
//
//    promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fegg%2FLTP%20Corn%20Egg%20Grade%20A%20?alt=media&token=7e489b4d-8508-4ede-b45a-55045e132146", "ABC_1234", "Free Orange", 10.90)
//    promotionList.add(promotion)
//
////    promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90)
////    promotionList.add(promotion)
////
////    promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90)
////    promotionList.add(promotion)
//
//        for (item in promotionList) {
//
//            var pushkey: String? = myRef.push().key
//
//
//            if (pushkey != null) {
//                item.setID(pushkey)
//                myRef.child(pushkey).setValue(item).addOnCompleteListener {
//
//
//                }
//            }
//
//
//        }
//
//    }

    fun readData(){
        // Write a message to the database

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Promotion/")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot : DataSnapshot) {

                for ( childDataSnapshot : DataSnapshot in dataSnapshot.getChildren()) {
                    myRef = database.getReference("Promotion/"+ childDataSnapshot.getKey().toString())
//                    Log.i("data", childDataSnapshot.getKey().toString()); //displays the key for the node

//                    Log.i("data",childDataSnapshot.child(childDataSnapshot.getKey().toString()).value.toString());
                    Log.i("data",childDataSnapshot.getValue(PromotionModel::class.java).toString());//gives the value for given keyname
                    childDataSnapshot.getValue(PromotionModel::class.java)?.let {promotionList.add(it) }
                    promotionAdapter.notifyItemInserted(promotionList.size)

//                        Log.i("data",myDataset.toString())
                }

            }

            override  fun onCancelled(databaseError : DatabaseError) {

            }
        });


    }

    fun readItem(){
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val ref = database.getReference("Profile/" + auth.currentUser!!.uid + "/")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user: User = dataSnapshot.getValue(User::class.java)!!

              //  username.setText(user.FirstName)
                username.setText("testing")
                Log.i("user", user.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        });
    }




//    private fun preparePromotionData() {
//
//        var promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90
//        )
//        promotionList.add(promotion)
//
//        promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90)
//        promotionList.add(promotion)
//
//        promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90)
//        promotionList.add(promotion)
//
//        promotion = PromotionModel("","https://firebasestorage.googleapis.com/v0/b/madassignment-1bc52.appspot.com/o/images%2Fchicken%2FAyam%20?alt=media&token=dabe8d8d-4230-4126-a52c-687d27248c33", "Hello", "Free Orange", 10.90)
//        promotionList.add(promotion)
//
//
//        promotionAdapter.notifyDataSetChanged()
//    }

}












