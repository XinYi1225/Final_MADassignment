package com.example.madassignment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.madassignment.adapter.CartAdapter
import com.example.madassignment.object_class.Cart
import com.example.madassignment.object_class.ShoppingCart
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class CartActivity : AppCompatActivity(), ButtonClickedListener {


    lateinit var adapter: CartAdapter
    lateinit var cart: ShoppingCart
    lateinit var cartpricetotal: TextView
    private var deleteList : MutableList<Cart> = mutableListOf()
    private var positionlist: MutableList<Int> = mutableListOf()
    private var cartlist: MutableList<Cart> = ShoppingCart.getCart()
    lateinit var checkAll: CheckBox
    lateinit var checkout_button : Button
    var subtotal : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartlist= ShoppingCart.getCart()
        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP;
        flexManager.flexDirection = FlexDirection.ROW;
        flexManager.alignItems = AlignItems.FLEX_START

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = CartAdapter(cartlist, this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = flexManager
        checkAll = findViewById(R.id.check_all)

        checkout_button = findViewById(R.id.checkout_button)
        checkAll.setOnClickListener {
            if (checkAll.isChecked) {
                adapter.selectAll();
            } else {
                adapter.unselectAll();
            }
        }

        val dustbin: ImageView = findViewById(R.id.delete_cart)
        dustbin.setOnClickListener {
            Log.i("clickdustbin", deleteList.toString())
            if((!(deleteList.isEmpty()) || adapter.isSelectedAll )){

                AlertDialog.Builder(this)
                    .setTitle("Delete Item Confirmation")
                    .setMessage("Are you sure?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            deleteItem()
                        })
                    .setNegativeButton("No", null).show()
            }

        }

        backTo()
        checkout()
        subtotal = ShoppingCart.calcTotal()
        cartpricetotal = findViewById(R.id.totalcartprice)
        cartpricetotal.setText("RM " + String.format("%.2f", subtotal))
    }
    fun deleteItem(){
        if(adapter.isSelectedAll){
            ShoppingCart.removeMoreThanOneItem(cartlist,this)
            checkAll.isChecked = false
            cartlist.clear()
            adapter.notifyDataSetChanged()
        }
        else{
            ShoppingCart.removeMoreThanOneItem(deleteList,this)
        }

        positionlist.sort()
        for(i in positionlist.indices.reversed()){
            Log.i("poslist", "cart size before:" + cartlist.size.toString() + "position before:" + positionlist[i].toString())
            cartlist.removeAt(positionlist[i])
//            adapter.notifyItemRemoved(i)
            adapter.notifyItemChanged(positionlist[i]);
            adapter.notifyItemRangeChanged(positionlist[i], cartlist.size)
            Log.i("poslist", "cart size :" + cartlist.size.toString() + "position:" + positionlist[i].toString())
        }
        positionlist.clear()
        cartpricetotal.setText("RM " + String.format("%.2f", ShoppingCart.calcTotal()))
    }

    fun backTo() {
        val back: ImageView = findViewById(R.id.arrow_back_icon)
        back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun minus(item: Cart, position: Int) {
        if (item.itemQty != 1) {
            item.itemQty -=1
            adapter.notifyItemChanged(position)
            ShoppingCart.updateCart(item, this)
            cartpricetotal.setText("RM " + String.format("%.2f", ShoppingCart.calcTotal()))
        } else {
            Toast.makeText(this, "Minimum quantity must be 1", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getCheckedItem(item: Cart, check: Boolean, position: Int) {
        Log.i("cartactivity", position.toString())
        if(check){
            deleteList.add(item)
            positionlist.add(position)
        }else{
            if(item in  deleteList){
                deleteList.remove(item)
                positionlist.remove(position)
            }
        }

        Log.i("delete", deleteList.toString())
    }


    override fun add(item: Cart, position: Int) {
        item.itemQty+= 1
        adapter.notifyItemChanged(position)
        ShoppingCart.updateCart(item, this)
        cartpricetotal.setText("RM " + String.format("%.2f", ShoppingCart.calcTotal()))
    }
    fun checkout(){
        checkout_button.setOnClickListener{
            if(ShoppingCart.getShoppingCartSize() != 0){
                AlertDialog.Builder(this)
                    .setTitle("Check Out Confirmation")
                    .setMessage("Are you sure?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            adapter.isSelectedAll = false
                            val intent  = Intent(this, DeliveryOrPickup::class.java)
                            startActivity(intent)
                        })
                    .setNegativeButton("No", null).show()


            }


        }


    }
}