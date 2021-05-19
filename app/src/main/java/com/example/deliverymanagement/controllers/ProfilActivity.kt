package com.example.deliverymanagement.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.example.deliverymanagement.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfilActivity : AppCompatActivity() {
    lateinit var nameT : TextView
    lateinit var email : TextView
    lateinit var phoneNumber : TextView
    lateinit var address : TextView
   lateinit var database: FirebaseDatabase
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var myRef: DatabaseReference
    lateinit var firebaseUser: FirebaseUser
    private lateinit var users: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        nameT=findViewById(R.id.nameP)
        users = ArrayList()
        database = FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser= firebaseAuth.currentUser!!
        myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val  td : Map<String, Object> = postSnapshot.getValue() as HashMap<String, Object>
                    val user = User(
                        td.get("userId").toString(),
                        td.get("name").toString(),
                        td.get("email").toString(),
                        td.get(
                            "password"
                        ).toString(),
                        td["phoneNumber"].toString().toInt(),
                        td.get("address").toString(),
                        "default"
                    )
                    users.add(user)
                }
                for(u in users){
                    if(u.userId==firebaseUser.uid){
                        nameT.text = u.name.toString()
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}