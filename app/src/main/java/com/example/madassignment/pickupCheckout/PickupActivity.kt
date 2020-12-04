package com.example.madassignment.pickupCheckout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.PaymentDoneActivity
import com.example.madassignment.R
import com.example.madassignment.delivery.DetailModel
import com.example.madassignment.delivery.HistoryAdapter
import com.example.madassignment.delivery.HistoryModel
import com.example.madassignment.object_class.ShoppingCart

import com.example.madassignment.pickupCheckout.PickupPayViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class PickupActivity : AppCompatActivity() {
    private lateinit var viewModel: PickupPayViewModel
    

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup)

        Log.i("CheckoutFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(PickupPayViewModel::class.java)
        viewModel.subtotal = ShoppingCart.calcTotal()
        getTransactionDate()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        var intent_address = intent
        val display_address = intent_address.getStringExtra("pickup_address")

        val pickup_address = findViewById<TextView>(R.id.pickup_address)

        pickup_address.text = display_address

        calculateTotal()

        //display toast message when customer did not select payment method
        findViewById<Button>(R.id.done_button).setOnClickListener {
            if(findViewById<RadioGroup>(R.id.payment_options).getCheckedRadioButtonId() == -1){
                // no radio buttons are checked
                Toast.makeText(applicationContext, "Please select a payment method!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "Payment has been made successfully!", Toast.LENGTH_SHORT).show()
                val payMethod: Int = findViewById<RadioGroup>(R.id.payment_options).getCheckedRadioButtonId()
                val selectedPayMethod: RadioButton? = findViewById<RadioButton>(payMethod)
                setCheckoutData(selectedPayMethod?.getText().toString())

                ShoppingCart.clear()

                val intent = Intent(this, PaymentDoneActivity::class.java)
                startActivity(intent)
            }
        }

        //Up button
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {
            onBackPressed()
        }
    }



    private fun calculateTotal() {
        viewModel.calculateTotal()
        updateTotal()
    }

    private fun updateTotal(): Double{
//        findViewById<TextView>(R.id.subtotal_amount).text = String.format("%.2f",viewModel.subtotal)
    //    findViewById<TextView>(R.id.shipping_amount).text = String.format("%.2f",viewModel.shippingFees)
        findViewById<TextView>(R.id.final_payment).text = String.format("%.2f",viewModel.totalPaid)

        return viewModel.totalPaid
    }

    fun getTransactionDate() : String{
        val format = "yyyy-MM-dd HH:mm"
        val sdf = SimpleDateFormat(format, Locale("ms", "MY"))
        val currentDateAndTime: String = sdf.format(Date())

        findViewById<TextView>(R.id.transaction_date).text = currentDateAndTime

        return currentDateAndTime
    }

    fun setCheckoutData(payMethod: String){
        //write checkout details into database
        val database = FirebaseDatabase.getInstance()
        var myRef: DatabaseReference = database.getReference("OrderHistory/")
        val itemList : MutableList<DetailModel> = mutableListOf()
//        val detailList : ArrayList<HistoryModel>? = null
        val cartlist = ShoppingCart.getCart()

        for(order in cartlist){
            var convertToItem = DetailModel(order.itemID, order.itemImage, order.itemName, order.itemQty, order.itemPrice)
            itemList.add(convertToItem)
        }
        var pushkey: String?= myRef.push().key

        val transactionDate = getTransactionDate()
        val status = "Pick up"
      //  val shipping_fees = 0.00
        val total_amt = updateTotal()

        //write the details of checkout into database
        myRef = database.getReference("Profile/"+auth.currentUser!!.uid+"/OrderHistory/" + pushkey + "/")

        Log.i("list", itemList.toString())

        var details = HistoryModel(pushkey.toString(), "${transactionDate}", itemList,ShoppingCart.getShoppingCartSize(),ShoppingCart.calcTotal(), 0.0,total_amt,"${payMethod}","${status}")
        Log.i("list", details.item_list.toString())
        myRef.setValue(details)
    }
}