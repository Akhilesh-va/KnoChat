package com.example.knochat

import User
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storageMetadata
import de.hdodenhof.circleimageview.CircleImageView

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1001
    }
    lateinit var emailPattern : String
    lateinit var auth :FirebaseAuth
    lateinit var database : DatabaseReference
    lateinit var store: StorageReference
    lateinit var users :User
    lateinit var progressDialog: ProgressDialog
    var imageUri: Uri? = null
    val rgUsername:EditText
        get() = findViewById(R.id.etUsername)
    val rgEmail : EditText
        get()=findViewById(R.id.etEmail)
    val rgPassword :EditText
        get() = findViewById(R.id.etPassword)
    val rgReEnterPassword : EditText
        get()=findViewById(R.id.etReenterpassword)
    val btnSignup : Button
        get()=findViewById(R.id.btnSignup)
    val profileImage :CircleImageView
        get() = findViewById(R.id.profile_image)

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        progressDialog = ProgressDialog(this).apply {
            setMessage("Establishing the account")
            setCancelable(false) // Prevent user from dismissing it
            android.os.Handler().postDelayed({
                progressDialog.dismiss() // Dismiss after 5 seconds
            }, 2000) // Delay for 5000 ms (5 seconds)

        }
        setContentView(R.layout.activity_sign_up)
        btnSignup.setOnClickListener{
            val name  = rgUsername.text.toString()
            val email = rgEmail.text.toString()
            val password = rgPassword.text.toString()
            val rePassword = rgReEnterPassword.text.toString()

             emailPattern = "[A-Za-z0-9._%+!$&*=^|~#%'`?{}\\-/]+@(gmail\\.com|[a-z0-9\\-]+\\.(com|org|net|edu|gov|io|co|in))"

            if(TextUtils.isEmpty(name) ||TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword) ){
                progressDialog.dismiss()
                Toast.makeText(this, "Please Do not leave blank", Toast.LENGTH_SHORT).show()
            }
            else if(!email.matches(Regex(emailPattern))){
                progressDialog.dismiss()
                rgEmail.error="Type valid email"
            }
            else if(password.length <6){
                progressDialog.dismiss()
                rgPassword.error="Password must be 6 Characters or More"
            }
            else if (!password.equals(rePassword)){
                progressDialog.dismiss()
                rgPassword.error = "Password Does not Match"
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task->
                    if(task.isSuccessful){
                        val id = task.result?.user?.uid;
                        val user = User(id.toString(),name,email,password,rePassword)
                        database=FirebaseDatabase.getInstance().getReference().child("Users").child(id.toString())
                       database.setValue(user)
                        progressDialog.show()
                        val intent = Intent(this,LoginActivity::class.java)
                        Toast.makeText(this, "User Registered ", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()

                    }
                }
            }




        }
        profileImage.setOnClickListener{
            val pickImageFileIntent = Intent()
            pickImageFileIntent.type = "image/*"
            pickImageFileIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(pickImageFileIntent,"Select Picture"),
                REQUEST_CODE_PICK_IMAGE)
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
             imageUri = data?.data // Get the selected image URI
            imageUri?.let {
                profileImage.setImageURI(it) // Set the image to the ImageView
            }
        }
    }
}