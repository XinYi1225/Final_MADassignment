package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madassignment.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    lateinit var  email_forgot_password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        //Email Address Validation
        email_forgot_password = findViewById(R.id.email_forgot_password)

        val customisedErrorIcon = resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.
        //val customisedErrorIcon = ContextCompat.getDrawable(this, R.drawable.error_icon_display);


        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )
        email_forgot_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!Patterns.EMAIL_ADDRESS.matcher(email_forgot_password.text.toString()).matches()){
                    email_forgot_password.setError("Invalid Email Address",customisedErrorIcon)
                }
            }
        })


        val reset_password_button = findViewById<Button>(R.id.reset_password_button)
        reset_password_button.setOnClickListener(this)

        //Up button
        val go_back = findViewById<ImageView>(R.id.arrow_back_icon)
        go_back.setOnClickListener {

            onBackPressed()
        }


    }

    private fun validate_forgot_password(): Boolean {

        val customisedErrorIcon = resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.
        val email_forgot_password = findViewById<EditText>(R.id.email_forgot_password)

        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )

        if(email_forgot_password.text.toString().isEmpty()){
            email_forgot_password.setError("Required Field!", customisedErrorIcon)
            return false
        }

        else return true


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reset_password_button ->{
                if(!validate_forgot_password())
                    Toast.makeText(applicationContext,"Invalid Email Address",Toast.LENGTH_LONG).show()

                else {
                    email_forgot_password = findViewById(R.id.email_forgot_password)
                    auth.sendPasswordResetEmail(email_forgot_password.text.toString())
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(applicationContext,"Email Sent",Toast.LENGTH_LONG).show()

                                startActivity(Intent(this, Login::class.java))
                                finish()

                            }
                        }
                }

            }

        }
    }
}