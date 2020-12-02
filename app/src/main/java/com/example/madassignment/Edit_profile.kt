package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Edit_profile : AppCompatActivity() {
    //    ,View.OnClickListener
    private lateinit var state_edit: Spinner

    lateinit var clickable_changePassword: TextView
    lateinit var clickable_save: ImageView
    lateinit var go_back: ImageView

    lateinit var firstName_edit: EditText
    lateinit var lastName_edit: EditText
    lateinit var gender_edit: TextView
    lateinit var phoneNo_edit: EditText
    lateinit var email_edit: TextView
    lateinit var dob_edit: TextView
    lateinit var address1_edit: EditText
    lateinit var town_edit: EditText
    lateinit var postalCode_edit: EditText
    lateinit var spinner_state_edit: AutoCompleteTextView

    private lateinit var mUser: User
    private lateinit var auth: FirebaseAuth
    //   private lateinit var database: DatabaseReference

    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    lateinit var currentpass: TextInputEditText
    lateinit var resetpass: TextInputEditText
    lateinit var confirmpass: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile2)

//        val state_malaysia = arrayOf<String?>(
//            "Johor",
//            "Kedah",
//            "Kelantan",
//            "Malacca",
//            "Negeri Sembilan",
//            "Pahang",
//            "Penang",
//            "Perak",
//            "Sabah",
//            "Sarawak",
//            "Selangor",
//            "Terengganu",
//            "W.P Kuala Lumpur",
//            "W.P Labuan",
//            "W.P Putrajaya"
//        )


        //state_edit = findViewById(R.id.state_edittext)

//        val arrayAdapter: ArrayAdapter<Any?> =
//            ArrayAdapter<Any?>(this, R.layout.spinner_list, state_malaysia)
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_list)
//        state_edit.adapter = arrayAdapter

        //Up button
        go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {
            onBackPressed()
        }


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        clickable_save = findViewById<ImageView>(R.id.save_icon)


//        readItem()
        clickable_save.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle_save)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage_save)
            builder.setIcon(R.drawable.account_save_icon)

            //performing positive action
            builder.setPositiveButton("Yes")
            { dialogInterface, which ->
                Toast.makeText(applicationContext, "Saving", Toast.LENGTH_LONG).show()
                saveData()

            }

            //performing negative action
            builder.setNegativeButton("No")
            { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()

            // Set other dialog properties
            alertDialog.show()


        }

        // Change Password
        clickable_changePassword = findViewById<TextView>(R.id.changePassword)

        clickable_changePassword.setOnClickListener {

            val intent_save = Intent(this, changePassword::class.java)
            startActivity(intent_save)

        }

        firstName_edit = findViewById(R.id.FirstName_edittext)
        lastName_edit = findViewById(R.id.lastName_edittext)
        gender_edit = findViewById(R.id.gender_edittext)
        phoneNo_edit = findViewById(R.id.phoneNo_edittext)
        email_edit = findViewById(R.id.email_edittext)
        dob_edit = findViewById(R.id.DOB_edittext)
        address1_edit = findViewById(R.id.address1_edittext)
        town_edit = findViewById(R.id.town_edittext)
        postalCode_edit = findViewById(R.id.postalCode_edittext)

        spinner_state_edit = findViewById(R.id.spinner_state_profile)


        var state = arrayOf(
            "Johor",
            "Kedah",
            "Kelantan",
            "Malacca",
            "Negeri Sembilan",
            "Pahang",
            "Penang",
            "Perak",
            "Sabah",
            "Sarawak",
            "Selangor",
            "Terengganu",
            "W.P Kuala Lumpur",
            "W.P Labuan",
            "W.P Putrajaya"
        )

        var adapter_state = ArrayAdapter(this, android.R.layout.simple_list_item_1, state)
        spinner_state_edit.threshold = 0
        spinner_state_edit.setAdapter(adapter_state)
        spinner_state_edit.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus -> if (hasFocus) spinner_state_edit.showDropDown() }

        //      auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().reference

        readItem()

    }

    fun readItem() {
        val database = FirebaseDatabase.getInstance()
//        val auth = FirebaseAuth.getInstance()
        val ref = database.getReference("Profile/" + auth.currentUser!!.uid + "/")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user: User = dataSnapshot.getValue(User::class.java)!!

                firstName_edit.setText(user.FirstName)
                lastName_edit.setText(user.LastName)
                gender_edit.text = user.Gender
                phoneNo_edit.setText(user.PhoneNumber)
                email_edit.text = user.Email
                dob_edit.text = user.DateofBirth
                address1_edit.setText(user.Address)
                town_edit.setText(user.Town)
                postalCode_edit.setText(user.PostalCode)
                spinner_state_edit.setText(user.State)

                Log.i("user", user.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun validate_save(): Boolean {


        if (firstName_edit.text.toString().isEmpty()) {
            firstName_edit.error = "Required Field!"
            firstName_edit.requestFocus()

            return false
        }

        if (lastName_edit.text.toString().isEmpty()) {
            lastName_edit.error = "Required Field!"
            lastName_edit.requestFocus()

            return false
        }


        if (phoneNo_edit.text.toString().isEmpty()) {
            phoneNo_edit.error = "Required Field!"
            phoneNo_edit.requestFocus()

            return false
        }

        if (address1_edit.text.toString().isEmpty()) {
            address1_edit.error = "Required Field!"
            address1_edit.requestFocus()

            return false
        }

        if (town_edit.text.toString().isEmpty()) {
            town_edit.error = "Required Field!"
            town_edit.requestFocus()

            return false
        }

        if (postalCode_edit.text.toString().isEmpty()) {
            postalCode_edit.error = "Required Field!"
            postalCode_edit.requestFocus()

            return false
        }

        if (spinner_state_edit.text.toString().isEmpty()) {
            spinner_state_edit.error = "Required Field!"
            spinner_state_edit.requestFocus()

            return false

        } else
            return true
    }

    private fun saveData() {


        if (!validate_save()) {


            Toast.makeText(applicationContext, "Incomplete Details!", Toast.LENGTH_LONG).show()
        } else {

            val user = auth.currentUser
            val userDatabase = databaseReference?.child((user?.uid!!))
            userDatabase?.child("FirstName")?.setValue(firstName_edit.text.toString())
            userDatabase?.child("LastName")?.setValue(lastName_edit.text.toString())
            userDatabase?.child("PhoneNumber")?.setValue(phoneNo_edit.text.toString())
            userDatabase?.child("Address")?.setValue(address1_edit.text.toString())
            userDatabase?.child("Town")?.setValue(town_edit.text.toString())
            userDatabase?.child("PostalCode")?.setValue(postalCode_edit.text.toString())
            userDatabase?.child("State")?.setValue(spinner_state_edit.text.toString())

            finish()
        }


    }

}