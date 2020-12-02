package com.example.madassignment.pickupCheckout

import android.util.Log
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class PickupPayViewModel : ViewModel() {

    ///subtotal amount
    var subtotal = 21.90

    //shipping fees
    var shippingFees = 0.00

    //total amount need to be paid
    var totalPaid = 0.00
    var formattedAmt = ""

    init {
        Log.i("PickupPayViewModel", "PickupPayViewModel created!")
    }

    fun calculateTotal() {
        totalPaid = subtotal + shippingFees
        formattedAmt = NumberFormat.getNumberInstance().format(totalPaid)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("PickupPayViewModel", "PickupPayViewModel destroyed!")
    }
}