package com.example.madassignment.deliveryCheckout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.madassignment.R
import com.example.madassignment.PaymentDoneActivity
import com.example.madassignment.User
import com.example.madassignment.delivery.DetailModel
import com.example.madassignment.delivery.HistoryAdapter
import com.example.madassignment.delivery.HistoryModel
import com.example.madassignment.deliveryCheckout.DeliverPayViewModel
import com.example.madassignment.object_class.ShoppingCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


import java.text.SimpleDateFormat
import java.util.*

class CheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: DeliverPayViewModel

    private val detailList = ArrayList<HistoryModel>()
    private val itemList = ArrayList<DetailModel>()
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var mUser: User
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    lateinit var delivery_address: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_2)
        Log.i("CheckoutFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(DeliverPayViewModel::class.java)
        viewModel.subtotal = ShoppingCart.calcTotal()
        getTransactionDate()

        delivery_address = findViewById(R.id.delivery_address)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {
            onBackPressed()
        }

        // here
        var intent_address = intent
        val display_address = intent_address.getStringExtra("data")

        if (display_address != null) {
            Log.i("display_address",display_address)
        }

        val delivery_address = findViewById<TextView>(R.id.delivery_address)

        delivery_address.text = display_address
//
//        findViewById<TextView>(R.id.request_edit_text).setOnClickListener {
//            hideKeyboard(it)
//        }

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

    }


    private fun calculateTotal() {
        viewModel.calculateTotal()
        updateTotal()
    }

    private fun updateTotal(): Double{
        findViewById<TextView>(R.id.subtotal_amount).text = String.format("%.2f",viewModel.subtotal)
        findViewById<TextView>(R.id.shipping_amount).text = String.format("%.2f",viewModel.shippingFees)
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
        val status = "Delivery"
        val shipping_fees = 10.00
        val total_amt = updateTotal()

        //write the details of checkout into database
        myRef = database.getReference("Profile/"+auth.currentUser!!.uid+"/OrderHistory/" + pushkey + "/")

        Log.i("list", itemList.toString())

        var details = HistoryModel("${pushkey}", "${transactionDate}", itemList,ShoppingCart.getShoppingCartSize(),ShoppingCart.calcTotal(), shipping_fees,total_amt,"${payMethod}","${status}")

        Log.i("list", details.item_list.toString())
        myRef.setValue(details)
    }

}





