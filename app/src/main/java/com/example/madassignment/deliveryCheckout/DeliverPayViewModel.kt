package com.example.madassignment.deliveryCheckout

import android.util.Log
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class DeliverPayViewModel : ViewModel() {
    //subtotal amount
    var subtotal = 21.90

    //shipping fees
    var shippingFees = 10.00

    //total amount need to be paid
    var totalPaid = 0.00
    var formattedAmt = ""


    init {
        Log.i("DeliverPayViewModel", "DeliverPayViewModel created!")
    }

    fun calculateTotal() {
        totalPaid = subtotal + shippingFees
        formattedAmt = NumberFormat.getNumberInstance().format(totalPaid)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("DeliverPayViewModel", "DeliverPayViewModel destroyed!")
    }
}