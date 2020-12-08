package com.example.madassignment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class changePassword : AppCompatActivity() {

    lateinit var go_back: ImageView

    lateinit var currentpass: TextInputEditText
    lateinit var newpass: TextInputEditText
    lateinit var confirmpass: TextInputEditText

    lateinit var save_btn: Button
    lateinit var cancel_btn: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()


        //Password
        currentpass = findViewById<TextInputEditText>(R.id.currentpass_dialog)
        newpass = findViewById<TextInputEditText>(R.id.newpass_dialog)
        confirmpass = findViewById<TextInputEditText>(R.id.confirmpass_dialog)

        val customisedErrorIcon =
            resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )

        newpass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val password: String = newpass.getText().toString()
                val PASSWORD_PATTERN =
                    Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}$")

                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    newpass.setError(
                        "Password does not comply to the requirement",
                        customisedErrorIcon
                    )
                    newpass.requestFocus()

                }
            }
        })

        //Confirmed Password
        confirmpass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val password: String = newpass.getText().toString()
                val confirmPassword: String = confirmpass.getText().toString()
                if (password != confirmPassword) {
                    confirmpass.setError("Password does not match", customisedErrorIcon)
                    confirmpass.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        save_btn = findViewById(R.id.dialogSaveBtn)
        save_btn.setOnClickListener {

            changePassword()
        }

        cancel_btn = findViewById(R.id.dialogCancelBtn)

        cancel_btn.setOnClickListener {

            // onBackPressed()

            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle_cancel)
            //set message for alert dialog
//            builder.setMessage(R.string.dialogMessage_cancel)
            builder.setIcon(R.drawable.account_cancel_24)

            //performing positive action
            builder.setPositiveButton("Yes")
            { dialogInterface, which ->
                Toast.makeText(applicationContext, "Cancelled changing password", Toast.LENGTH_LONG)
                    .show()
                onBackPressed()

            }

            //performing negative action
            builder.setNegativeButton("No")
            { dialogInterface, which ->
                Toast.makeText(applicationContext, "Continue to change password", Toast.LENGTH_LONG)
                    .show()


            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()

            // Set other dialog properties
            alertDialog.show()

        }

        go_back = findViewById(R.id.arrow_back)
        go_back.setOnClickListener {
            onBackPressed()
        }


    }

    private fun changePassword() {

        currentpass = findViewById<TextInputEditText>(R.id.currentpass_dialog)
        newpass = findViewById<TextInputEditText>(R.id.newpass_dialog)
        confirmpass = findViewById<TextInputEditText>(R.id.confirmpass_dialog)

        val customisedErrorIcon =
            resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )

        var check_error = 0

        if (currentpass.text.toString().isEmpty()) {
            currentpass.setError("Required Field!", customisedErrorIcon)
            //currentpass.requestFocus()

            check_error += 1
        }
        if (newpass.text.toString().isEmpty()) {
            newpass.setError("Required Field!", customisedErrorIcon)
            // newpass.requestFocus()

            check_error += 1
        }

        if (confirmpass.text.toString().isEmpty()) {
            confirmpass.setError("Required Field!", customisedErrorIcon)
            // confirmpass.requestFocus()

            check_error += 1
        }

        val password: String = newpass.getText().toString()
        val PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}$")

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            newpass.setError(
                "Password does not comply to the requirement",
                customisedErrorIcon
            )
           // newpass.requestFocus()
            check_error += 1

        }

        val confirmPassword: String = confirmpass.getText().toString()
        if (password != confirmPassword) {
            confirmpass.setError("Password does not match", customisedErrorIcon)
            //confirmpass.requestFocus()

            check_error += 1
        }

        if (check_error == 0) {
            val user = auth.currentUser!!
            if (user != null && user.email != null) {
                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.
                val credential = EmailAuthProvider
                    .getCredential(user.email!!, currentpass.text.toString())

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(this, "Re-authentication success", Toast.LENGTH_SHORT)
                                .show()

                            val user = auth.currentUser
                            val newPassword = "SOME-SECURE-PASSWORD"

                            user!!.updatePassword(newpass.text.toString())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Password changed successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(Intent(this, View_Profile::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Password changed failed",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }

                                }

                        } else {
                            Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT)
                                .show()
                            currentpass.setError("Current Password incorrect!", customisedErrorIcon)
                            currentpass.requestFocus()
                        }
                    }
            }


        }


    }

}