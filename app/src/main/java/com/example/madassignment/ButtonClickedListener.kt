package com.example.madassignment

import com.example.madassignment.object_class.Cart


interface ButtonClickedListener {
    fun add(item: Cart, position : Int)
    fun minus(item: Cart, position: Int)
    fun getCheckedItem(item:Cart,check : Boolean, position: Int)
}