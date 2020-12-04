package com.example.madassignment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.paperdb.Paper

class Login : AppCompatActivity() {

    lateinit var email_login:TextInputEditText
    lateinit var password_login:TextInputEditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        Paper.init(this)

        email_login =findViewById(R.id.email_login)
        password_login =findViewById(R.id.password_login)

        auth = FirebaseAuth.getInstance()

        // get reference to textview
        val clickable_signup = findViewById(R.id.clickable_signup) as TextView

        // set on-click listener
        clickable_signup.setOnClickListener {

            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        //FORGOT PASSWORD
        val clickable_forgot_password = findViewById(R.id.clickable_forgot_password) as TextView

        clickable_forgot_password.setOnClickListener {

            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        var sharedPreferences: SharedPreferences
        var editor: SharedPreferences.Editor

        sharedPreferences=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        val mail = sharedPreferences.getString("email", "")
        val passwords = sharedPreferences.getString("passowrd", "")
        val login_button = findViewById<Button>(R.id.login_button)

        email_login.setText(mail);
        password_login.setText(passwords);

        login_button.setOnClickListener{

            if(!validate_login())
                Toast.makeText(applicationContext,"Invalid Login!", Toast.LENGTH_LONG).show()

            else {

                auth.signInWithEmailAndPassword(
                    email_login.text.toString(),
                    password_login.text.toString())
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {

                            val login_checkBox = findViewById<CheckBox>(R.id.login_checkBox)

                            if(login_checkBox.isChecked()){
                                editor.putString("email",email_login.getText().toString());
                                editor.putString("passowrd",password_login.getText().toString());
                                editor.apply();
                            }else{
                                editor.putString("email","");
                                editor.putString("passowrd","");
                                editor.apply();
                            }


                            startActivity(Intent(this@Login,MainActivity::class.java))
                            finish()
                        }

                        else {
                            Toast.makeText(applicationContext, "Invalid Login!", Toast.LENGTH_LONG).show()
                        }
                    }


            }



        }

        email_login.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email_login.text.toString()).matches())

                else{
                    email_login.setError("Invalid Email Address!")
                }
            }
        })



    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }

    private fun validate_login(): Boolean {


        val customisedErrorIcon = resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )

        if (email_login.text.toString().isEmpty()) {
            email_login.setError("Required Field!",customisedErrorIcon)

            return false
        }

        if (password_login.text.toString().isEmpty()) {
            password_login.setError("Required Field!",customisedErrorIcon)

            return false
        }

        else
            return true

    }


}