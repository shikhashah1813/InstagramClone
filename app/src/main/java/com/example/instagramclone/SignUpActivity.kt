package com.example.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        button_login.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        button_register.setOnClickListener {
            CreateAccount()
        }
    }

    private fun CreateAccount() {

        val name = text_full_name.text.toString()
        val username = text_username.text.toString()
        val email = text_email.text.toString()
        val password = text_password.text.toString()

        when {
            TextUtils.isEmpty(name) -> Toast.makeText(this, "Name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(username) -> Toast.makeText(this, "Username is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()

            else -> {

                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait")
                progressDialog.show()

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //store credentials
                        saveUserInfo(name, username, email, progressDialog)
                    }
                    else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun saveUserInfo(name: String, username: String, email: String, progressDialog: ProgressDialog) {
        val currentUserId = mAuth.currentUser!!.uid
        val userRef = db.reference.child("User")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["name"] = name
        userMap["username"] = username
        userMap["email"] = email
        userMap["bio"] = "Hey, I'm using Instagram Clone."
        userMap["image"] = "gs://instagramclone-5e708.appspot.com/Default Images/default_avatar.png"

        userRef.child(currentUserId).setValue(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressDialog.dismiss()
                Toast.makeText(this, "Account has been created", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                val message = task.exception!!.toString()
                Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                mAuth.signOut()
                progressDialog.dismiss()
            }
        }
    }
}