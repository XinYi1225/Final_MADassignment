package com.example.madassignment.myPurchase

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.R
import com.example.madassignment.delivery.DetailModel
import com.example.madassignment.delivery.HistoryModel
import com.example.madassignment.delivery.PurchaseAdapter

import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class MyPurchaseActivity  : AppCompatActivity() {

    private var purchaseList = HistoryModel()
    private lateinit var purchaseAdapter: PurchaseAdapter

    var key = ""
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_purchase)
        auth = FirebaseAuth.getInstance()
        //Up button
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {

            onBackPressed()
        }

        if (intent.extras != null) {
            key = intent.getStringExtra("Item").toString()
//            getPurchaseData()
//
//            Glide.with(this).load(item.itemImage).into(itemImage)
        }

        getData()
        purchaseAdapter = PurchaseAdapter(purchaseList.item_list, this)
        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP
        flexManager.flexDirection = FlexDirection.ROW
        flexManager.alignItems = AlignItems.FLEX_START

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = flexManager
        recyclerView.adapter = purchaseAdapter

    }

    fun getData(){
        var item = HistoryModel()
        val database = FirebaseDatabase.getInstance()
        var myRef: DatabaseReference = database.getReference("Profile/"+ auth.currentUser!!.uid + "/OrderHistory/" + key + "/")

        var getData = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                item  = snapshot.getValue(HistoryModel::class.java)!!

                //get total info data
                findViewById<TextView>(R.id.transaction_date).text = item.transaction_date
                findViewById<TextView>(R.id.total_item).text = item.total_item.toString()
                findViewById<TextView>(R.id.payment_method).text = item.payment_method
                findViewById<TextView>(R.id.status).text = item.status
                val subtotal : TextView = findViewById(R.id.subtotal_amt)
                val shipping_amt : TextView = findViewById(R.id.shipping_amt)
                val total_order_amt : TextView = findViewById(R.id.total_order_amt)
                subtotal.setText(String.format("%.2f",item.subtotal_amt))
                shipping_amt.setText(String.format("%.2f",item.shipping_fees))
                total_order_amt.setText(String.format("%.2f",item.total_amt))
                Log.i("string",String.format("%.2f",item.subtotal_amt) )

                //get recycler view data
                for(i in item.item_list){
                    purchaseList.item_list.add(i)
                    purchaseAdapter.notifyItemInserted(purchaseList.item_list.size)
                }
            }
        }

        myRef.addValueEventListener(getData)


    }
}