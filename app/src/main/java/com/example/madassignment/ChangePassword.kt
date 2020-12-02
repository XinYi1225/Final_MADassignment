package com.example.madassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.google.android.libraries.places.internal.it
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePassword : AppCompatActivity() {


    lateinit var currentpass: TextInputEditText
    lateinit var resetpass: TextInputEditText
    lateinit var confirmpass: TextInputEditText

    lateinit var save_btn: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)


        auth = FirebaseAuth.getInstance()


        save_btn = findViewById(R.id.dialogSaveBtn)
        save_btn.setOnClickListener{
            changePassword()
        }
    }


    private fun changePassword() {
       // val chgPass_dialog = LayoutInflater.from(this).inflate(R.layout.change_password_dialog, null)


        currentpass = findViewById<TextInputEditText>(R.id.currentpass_dialog)
        resetpass = findViewById<TextInputEditText>(R.id.newpass_dialog)
        confirmpass = findViewById<TextInputEditText>(R.id.confirmpass_dialog)


        val customisedErrorIcon =
            resources.getDrawable(R.drawable.error_icon_display) //getDrawable(int, Resources.Theme) instead.

        customisedErrorIcon?.setBounds(
            0, 0,
            customisedErrorIcon.intrinsicWidth,
            customisedErrorIcon.intrinsicHeight
        )

        if (currentpass.text.toString().isNotEmpty()
            && resetpass.text.toString().isNotEmpty()
            && confirmpass.text.toString().isNotEmpty()
        ) {

            if (resetpass.text.toString().equals(confirmpass.text.toString())) {
                val user = auth.currentUser!!
                if (user !=null && user.email != null)
                {
                    // Get auth credentials from the user for re-authentication. The example below shows
                    // email and password credentials but there are multiple possible providers,
                    // such as GoogleAuthProvider or FacebookAuthProvider.
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentpass.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful){

                                Toast.makeText(this, "Re-authentication success", Toast.LENGTH_SHORT).show()

                                val user = auth.currentUser
                                val newPassword = "SOME-SECURE-PASSWORD"

                                user!!.updatePassword(resetpass.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()

                                            // auth.signOut()
                                        }
                                    }

                            }
                            else
                                Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT).show()
                        }
                }




            }

        } else {
            currentpass.setError("Required Field!", customisedErrorIcon)
            currentpass.requestFocus()
        }

    }
}