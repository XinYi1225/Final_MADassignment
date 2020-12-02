package com.example.madassignment.object_class

import java.io.Serializable

class Item(var itemID: String = "", var itemName: String = "", var itemImage: String = "",
           var itemPrice: Double = 0.0, var itemSellQty_kg: Double = 0.0,
           var itemQtyRemain_kg_or_pcs: Double = 0.0
) : Serializable {
}