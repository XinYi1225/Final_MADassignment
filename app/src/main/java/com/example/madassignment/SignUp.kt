package com.example.madassignment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.regex.Pattern


class SignUp : AppCompatActivity(),View.OnClickListener {

    lateinit var firstName_signup:TextInputEditText
    lateinit var lastName_signup:TextInputEditText
    lateinit var spinner_gender_signup:AutoCompleteTextView
    lateinit var phoneNumber_signup:TextInputEditText
    lateinit var email_signup:TextInputEditText
    lateinit var password_signup:TextInputEditText
    lateinit var confirmPassword_signup:TextInputEditText
    lateinit var dob_signup:TextInputEditText
    lateinit var address1_signup:TextInputEditText
    lateinit var town_signup:TextInputEditText
    lateinit var postalCode_signup:TextInputEditText
    lateinit var spinner_state_signup:AutoCompleteTextView

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firstName_signup =findViewById(R.id.firstName_signup)
        lastName_signup =findViewById(R.id.lastName_signup)
        spinner_gender_signup =findViewById(R.id.spinner_gender_signup)
        phoneNumber_signup =findViewById(R.id.phoneNumber_signup)
        email_signup =findViewById(R.id.email_signup)
        password_signup =findViewById(R.id.password_signup)
        confirmPassword_signup =findViewById(R.id.confirmPassword_signup)
        dob_signup =findViewById(R.id.dob_signup)
        address1_signup =findViewById(R.id.address1_signup)
        town_signup =findViewById(R.id.town_signup)
        postalCode_signup =findViewById(R.id.postalCode_signup)
        spinner_state_signup =findViewById(R.id.spinner_state_signup)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        //Date of Birth
        //val dob_signup = findViewById<TextInputEditText>(R.id.dob_signup)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Spinner (state)
        var state = arrayOf("Johor", "Kedah","Kelantan", "Malacca", "Negeri Sembilan", "Pahang", "Penang", "Perak", "Sabah", "Sarawak", "Selangor", "Terengganu", "W.P Kuala Lumpur", "W.P Labuan", "W.P Putrajaya")
        var gender = arrayOf("Male", "Female")

        var adapter_state = ArrayAdapter(this, android.R.layout.simple_list_item_1, state)

        spinner_state_signup.threshold = 0
        spinner_state_signup.setAdapter(adapter_state)
        spinner_state_signup.setOnFocusChangeListener(View.OnFocusChangeListener { view, hasFocus -> if (hasFocus) spinner_state_signup.showDropDown() })

        var adapter_gender = ArrayAdapter(this, android.R.layout.simple_list_item_1, gender)

        spinner_gender_signup.threshold = 0
        spinner_gender_signup.setAdapter(adapter_gender)
        spinner_gender_signup.setOnFocusChangeListener(View.OnFocusChangeListener { view, hasFocus -> if (hasFocus) spinner_gender_signup.showDropDown() })


        //Up button
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {

            onBackPressed()
        }


        //Date of Birth

        dob_signup.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay -> dob_signup.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear) }, year, month, day)
            dpd.show()
        }

        //Terms and Conditions
        val text_tc_signup = findViewById(R.id.text_tc_signup) as TextView

        text_tc_signup.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.tc_popup, null);

            //Alert Dialog Builder
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)

            //Show dialog
            val mAlertDialog = mBuilder.show()

            val close_icon = mAlertDialog.findViewById(R.id.close_icon) as ImageView
            close_icon.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }

        }



        val customisedErrorIcon = resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
                0, 0,
                customisedErrorIcon.intrinsicWidth,
                customisedErrorIcon.intrinsicHeight
        )

        //Email Address Validation

        email_signup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(email_signup.text.toString().isEmpty())
                    email_signup.setError("Required Field",customisedErrorIcon)

                else if(!Patterns.EMAIL_ADDRESS.matcher(email_signup.text.toString()).matches()){
                    email_signup.setError("Invalid Email Address",customisedErrorIcon)
                }
            }
        })

        //Confirmed Password
        confirmPassword_signup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val password: String = password_signup.getText().toString()
                val confirmPassword: String = confirmPassword_signup.getText().toString()
                if (password != confirmPassword) {
                    confirmPassword_signup.setError("Password does not match",customisedErrorIcon)
                    confirmPassword_signup.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        //Password
        password_signup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {        }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val password: String = password_signup.getText().toString()
                val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}$")

                if(!PASSWORD_PATTERN.matcher(password).matches()){
                    password_signup.setError("Password does not comply to the requirement",customisedErrorIcon)
                    password_signup.requestFocus()

                }
            }
        })

        val signup_button = findViewById<Button>(R.id.signup_button)
        signup_button.setOnClickListener(this)

    }


    private fun validate_signup(): Boolean{


        val customisedErrorIcon = resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
                0, 0,
                customisedErrorIcon.intrinsicWidth,
                customisedErrorIcon.intrinsicHeight
        )

        if(firstName_signup.text.toString().isEmpty()){
            firstName_signup.setError("Required Field!", customisedErrorIcon)
            firstName_signup.requestFocus()

            return false
        }

        if(lastName_signup.text.toString().isEmpty()){
            lastName_signup.setError("Required Field!", customisedErrorIcon)
            lastName_signup.requestFocus()

            return false
        }

        if(spinner_gender_signup.text.toString().isEmpty()){
            spinner_gender_signup.setError("Required Field!", customisedErrorIcon)
            spinner_gender_signup.requestFocus()

            return false
        }

        if(phoneNumber_signup.text.toString().isEmpty()){
            phoneNumber_signup.setError("Required Field!", customisedErrorIcon)
            firstName_signup.requestFocus()

            return false
        }

        if(email_signup.text.toString().isEmpty()){
            email_signup.setError("Required Field!", customisedErrorIcon)
            email_signup.requestFocus()
            return false
        }

        if(password_signup.text.toString().isEmpty()){
            password_signup.setError("Required Field!", customisedErrorIcon)
            password_signup.requestFocus()

            return false
        }

        if(confirmPassword_signup.text.toString().isEmpty()){
            confirmPassword_signup.setError("Required Field!", customisedErrorIcon)
            confirmPassword_signup.requestFocus()

            return false
        }

        if(dob_signup.text.toString().isEmpty()){
            dob_signup.setError("Required Field!", customisedErrorIcon)
            dob_signup.requestFocus()

            return false
        }

        if(address1_signup.text.toString().isEmpty()){
            address1_signup.setError("Required Field!", customisedErrorIcon)
            address1_signup.requestFocus()

            return false
        }

        if(town_signup.text.toString().isEmpty()){
            town_signup.setError("Required Field!", customisedErrorIcon)
            town_signup.requestFocus()

            return false
        }

        if(postalCode_signup.text.toString().isEmpty()){
            postalCode_signup.setError("Required Field!", customisedErrorIcon)
            postalCode_signup.requestFocus()

            return false
        }

        if(spinner_state_signup.text.toString().isEmpty()){
            spinner_state_signup.setError("Required Field!", customisedErrorIcon)
            spinner_state_signup.requestFocus()

            return false

        }

        val password: String = password_signup.getText().toString()
        val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}$")

        if(!PASSWORD_PATTERN.matcher(password).matches()){
            password_signup.setError("Password does not comply to the requirement",customisedErrorIcon)
            password_signup.requestFocus()
            return false

        }

        val confirmPassword: String = confirmPassword_signup.getText().toString()
        if (password != confirmPassword) {
            confirmPassword_signup.setError("Password does not match",customisedErrorIcon)
            confirmPassword_signup.requestFocus()
            return false
        }


        else
            return true
    }

    override fun onClick(v: View?) {
        val checkBox_tc_signup = findViewById<CheckBox>(R.id.checkBox_tc_signup)
        when(v?.id){
            R.id.signup_button ->{
                if(!validate_signup())
                    Toast.makeText(applicationContext,"Incomplete Details!",Toast.LENGTH_LONG).show()

                else if(!checkBox_tc_signup.isChecked()){
                    Toast.makeText(applicationContext,"Please tick the checkbox!",Toast.LENGTH_LONG).show()
                }

                else {
                    auth.createUserWithEmailAndPassword(
                            email_signup.text.toString(),
                            password_signup.text.toString()
                    )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val currentUser = auth.currentUser
                                    val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                                    currentUserDb?.child("FirstName")?.setValue(firstName_signup.text.toString())
                                    currentUserDb?.child("LastName")?.setValue(lastName_signup .text.toString())
                                    currentUserDb?.child("Gender")?.setValue(spinner_gender_signup .text.toString())
                                    currentUserDb?.child("PhoneNumber")?.setValue(phoneNumber_signup .text.toString())
                                    currentUserDb?.child("Email")?.setValue(email_signup .text.toString())
                                    currentUserDb?.child("DateofBirth")?.setValue(dob_signup .text.toString())
                                    currentUserDb?.child("Address")?.setValue(address1_signup  .text.toString())
                                    currentUserDb?.child("Town")?.setValue(town_signup .text.toString())
                                    currentUserDb?.child("PostalCode")?.setValue(postalCode_signup .text.toString())
                                    currentUserDb?.child("State")?.setValue(spinner_state_signup .text.toString())

                                    Toast.makeText(applicationContext, "Registered successfully!", Toast.LENGTH_LONG).show()

                                    startActivity(Intent(this, Login::class.java))
                                    finish()
                                }
                            }
                }
            }
        }
    }
}



