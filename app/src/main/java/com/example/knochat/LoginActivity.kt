package com.example.knochat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val btnLogin :Button
        get() = findViewById(R.id.btnLogin)
    val etEmail :EditText
        get() = findViewById(R.id.editTextTextEmailAddress)
    val etPassword :EditText
        get() = findViewById(R.id.editTextTextPassword)
    val btnSignup : TextView
        get() = findViewById(R.id.tvNeedSignUp)

    lateinit var auth :FirebaseAuth
    lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading, please wait...")
            setCancelable(false) // Prevent user from dismissing it
            android.os.Handler().postDelayed({
                progressDialog.dismiss() // Dismiss after 5 seconds
            }, 2000) // Delay for 5000 ms (5 seconds)
        }


        btnSignup.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener{
            val loginEmail : String = etEmail.text.toString()
            val loginPassword : String = etPassword.text.toString()
            val emailPattern = "[A-Za-z0-9._%+!$&*=^|~#%'`?{}\\-/]+@(gmail\\.com|[a-z0-9\\-]+\\.(com|org|net|edu|gov|io|co|in))"




            if(loginEmail.isEmpty() || loginPassword.isEmpty()){
                progressDialog.dismiss()
                Toast.makeText(this, "PLease Enter Email and Password", Toast.LENGTH_SHORT).show()
            }else if(!loginEmail.matches(Regex(emailPattern))){
                progressDialog.dismiss()
                etEmail.error="Invalid Email Format"
            }else if(loginPassword.length<6){
                progressDialog.dismiss()
                etPassword.error="More than 6 Characters"
            }

            else{
                auth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnSuccessListener {
                    progressDialog.show()
                    intent = Intent(this,MainActivity::class.java)
                    startActivity(intent);
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
                }
            }




    }
}}