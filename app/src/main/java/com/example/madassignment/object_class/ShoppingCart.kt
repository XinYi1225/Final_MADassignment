package com.example.madassignment.object_class

import io.paperdb.Paper
import android.content.Context

class ShoppingCart {
    companion object {
        fun addItem(cartItem: Cart) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.itemID == cartItem.itemID }
            if (targetItem == null) {

                cart.add(cartItem)
            } else {
                targetItem.itemQty += cartItem.itemQty
            }
            ShoppingCart.saveCart(cart)
        }

        fun removeItem(cartItem: Cart, context: Context) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.itemID == cartItem.itemID }
            if (targetItem != null) {
                if (targetItem.itemQty > 0) {
                    targetItem.itemQty -= 1
                }
//                else {
//                    cart.remove(targetItem)
//                }
            }


            ShoppingCart.saveCart(cart)
        }

        fun removeMoreThanOneItem(cartItem: List<Cart>, context: Context) {
            val cart = ShoppingCart.getCart()
            for (item in cartItem) {
                val targetitem = cart.singleOrNull { item.itemID == it.itemID }
                if (targetitem != null) {
                    cart.remove(targetitem)
                }
            }
            ShoppingCart.saveCart(cart)
        }

        fun saveCart(cart: MutableList<Cart>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<Cart> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.itemQty;
            }

            return cartSize
        }

        fun updateCart(cartItem: Cart, context: Context) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.itemID == cartItem.itemID }
            if (targetItem != null) {
                if (targetItem.itemQty > 0) {
                    targetItem.itemQty = cartItem.itemQty
                }

            }

            ShoppingCart.saveCart(cart)
        }

        fun calcTotal(): Double {
            val cart = ShoppingCart.getCart()
            var payment: Double = 0.0
            for (item in cart) {
                payment += item.itemPrice * item.itemQty
            }
            return payment
        }

        fun clear(){
            val cart = ShoppingCart.getCart()
            cart.clear()
            ShoppingCart.saveCart(cart)
        }
    }
}