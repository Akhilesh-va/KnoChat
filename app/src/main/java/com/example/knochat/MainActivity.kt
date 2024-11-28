package com.example.knochat

import User
import UserAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var mainUserRecyclerView : RecyclerView
    lateinit var adapter : UserAdapter
    lateinit var databse : FirebaseDatabase
    lateinit var refrence : DatabaseReference
    lateinit var userArrayList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth =FirebaseAuth.getInstance()
        refrence = databse.getReference().child("user")
        userArrayList = arrayListOf<User>()
        fetchUsers()


        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView)
        mainUserRecyclerView.layoutManager = LinearLayoutManager(this)
        mainUserRecyclerView.adapter = UserAdapter(userArrayList,this)
        if(auth.currentUser==null){
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }
    private fun fetchUsers() {
        refrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userArrayList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Log error or show a message
                error.message?.let { println("Database error: $it") }
            }
        })
    }
}