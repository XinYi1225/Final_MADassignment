package com.example.madassignment.delivery

import java.io.Serializable

class HistoryModel(

        var order_id: String = "",
        var transaction_date: String = "",
        var item_list: MutableList<DetailModel> = mutableListOf(),
        var total_item: Int = 0,
        var subtotal_amt: Double = 0.00,
        var shipping_fees: Double = 0.00,
        var total_amt: Double = 0.00,
        var payment_method: String = "",
        var status: String = ""

) : Serializable {

}