package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class Edit_profile2 : AppCompatActivity() {
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
        //     clickable_save.setOnClickListener(this)


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

//        clickable_save.setOnClickListener {
//
//            Toast.makeText(applicationContext, "Saving...", Toast.LENGTH_SHORT).show()
//            Toast.makeText(applicationContext, "Save successfully", Toast.LENGTH_SHORT).show()
//
//            onBackPressed()
////            val intent_save = Intent(this, View_Profile::class.java)
////            startActivity(intent_save)
//        }

        // Change Password
        clickable_changePassword = findViewById<TextView>(R.id.changePassword)

        clickable_changePassword.setOnClickListener {

            val intent_save = Intent(this, changePassword2::class.java)
            startActivity(intent_save)


//            val chgPass_dialog =
//                LayoutInflater.from(this).inflate(R.layout.change_password_dialog, null)
//            //AlertDialogBuilder
//            val builder_AD = AlertDialog.Builder(this)
//            builder_AD.setView(chgPass_dialog)
//            //builder_AD.setTitle("Change Password")
//
//            //show dialog
//            val mAlertDialog = builder_AD.show()

//
//            val reset_pasword =
//                chgPass_dialog.findViewById<TextInputEditText>(R.id.resetpass_dialog)
//            reset_pasword.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable) {}
//
//                override fun beforeTextChanged(
//                    s: CharSequence,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//
//                val reset_layout_Password =
//                    chgPass_dialog.findViewById<TextInputLayout>(R.id.resetpass_layout_dialog)
//                val reset_Password =
//                    chgPass_dialog.findViewById<TextInputEditText>(R.id.resetpass_dialog)
//
//
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//                    val customisedErrorIcon =
//                        resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.
//
//                    customisedErrorIcon?.setBounds(
//                        0, 0,
//                        customisedErrorIcon.intrinsicWidth,
//                        customisedErrorIcon.intrinsicHeight
//                    )
//
//                    val password: String = reset_pasword.text.toString()
//
//
//                    val PASSWORD_PATTERN =
//                        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$")
//                    val matcher = PASSWORD_PATTERN.matcher(password)
////
//                    if (!PASSWORD_PATTERN.matcher(password).matches()) {
//                        reset_Password.setError(
//                            "Password does not comply to the requirement",
//                            customisedErrorIcon
//                        )
//                        //reset_layout_Password.setError("Password does not comply to the requirement")
//                    }
//
//                }
//            })
//
//            //Confirmed Password
//            val confirm_password =
//                chgPass_dialog.findViewById<TextInputEditText>(R.id.confirmpass_dialog)
//
//
//            confirm_password.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable) {
//
//                    val customisedErrorIcon =
//                        resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.
//
//                    customisedErrorIcon?.setBounds(
//                        0, 0,
//                        customisedErrorIcon.intrinsicWidth,
//                        customisedErrorIcon.intrinsicHeight
//                    )
//
//                    val password: String = reset_pasword.text.toString()
//                    val confirmPassword: String = confirm_password.text.toString()
//                    if (password != confirmPassword) {
//                        confirm_password.setError("Password does not match", customisedErrorIcon)
//                        //   confirm_password.setError("Password does not match")
//                        //   confirm_layout_Password.setError("Password does not match")
//
//                    }
//
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            })



//            val chgPass_save_btn = chgPass_dialog.findViewById(R.id.dialogSaveBtn) as Button
//
//            chgPass_save_btn.setOnClickListener{
//                changePassword()
//            }




//

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

//    private fun changePassword() {
//        val chgPass_dialog = LayoutInflater.from(this).inflate(R.layout.change_password_dialog, null)
//
//
//        currentpass = chgPass_dialog.findViewById<TextInputEditText>(R.id.currentpass_dialog)
//        resetpass = chgPass_dialog.findViewById<TextInputEditText>(R.id.resetpass_dialog)
//        confirmpass = chgPass_dialog.findViewById<TextInputEditText>(R.id.confirmpass_dialog)
//
//
//        val customisedErrorIcon =
//            resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.
//
//        customisedErrorIcon?.setBounds(
//            0, 0,
//            customisedErrorIcon.intrinsicWidth,
//            customisedErrorIcon.intrinsicHeight
//        )
//
//        if (currentpass.text.toString().isNotEmpty()
//            && resetpass.text.toString().isNotEmpty()
//            && confirmpass.text.toString().isNotEmpty()
//        ) {
//
//            if (resetpass.text.toString().equals(confirmpass.text.toString())) {
//                val user = auth.currentUser
//                if (user !=null && user.email != null)
//                {
//                    // Get auth credentials from the user for re-authentication. The example below shows
//                    // email and password credentials but there are multiple possible providers,
//                    // such as GoogleAuthProvider or FacebookAuthProvider.
//                    val credential = EmailAuthProvider
//                        .getCredential(user.email!!, currentpass.text.toString())
//
//                    // Prompt the user to re-provide their sign-in credentials
//                    user.reauthenticate(credential)
//                        .addOnCompleteListener {
//                            if (it.isSuccessful){
//
//                                Toast.makeText(this, "Re-authentication success", Toast.LENGTH_SHORT).show()
//
//                                val user = auth.currentUser
//                                val newPassword = "SOME-SECURE-PASSWORD"
//
//                                user!!.updatePassword(resetpass.text.toString())
//                                    .addOnCompleteListener { task ->
//                                        if (task.isSuccessful) {
//                                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
//
//                                           // auth.signOut()
//                                        }
//                                    }
//
//                            }
//                            else
//                                Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT).show()
//                        }
//                }
//
//
//
//
//            }
//
//        } else {
//            currentpass.setError("Required Field!", customisedErrorIcon)
//            currentpass.requestFocus()
//        }
//
//    }


}