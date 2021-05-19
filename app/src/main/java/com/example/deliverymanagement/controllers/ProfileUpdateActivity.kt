package com.example.deliverymanagement.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileUpdateActivity : AppCompatActivity() {
    lateinit var nameT : EditText
    lateinit var phoneNumberT : EditText
    lateinit var addressT: EditText
    lateinit var database: FirebaseDatabase
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var myRef: DatabaseReference
    lateinit var firebaseUser: FirebaseUser
    private lateinit var users: ArrayList<User>
    private lateinit var update:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)
        var pwdv:String=""
        var imgv:String=""
        var emailv:String=""
        nameT=findViewById(R.id.name)
        phoneNumberT=findViewById(R.id.phoneNumber)
        addressT=findViewById(R.id.address)
        update=findViewById(R.id.profil_update)
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
                for(user in users){
                    if (user.userId == firebaseUser.uid) {
                        nameT.setText(user.name.toString())
                        phoneNumberT.setText(user.phoneNumber.toString())
                        addressT.setText(user.address.toString())
                    }
                }

                for(user in users) {
                    if (user.userId == firebaseUser.uid) {
                        update.setOnClickListener(View.OnClickListener {
                            val name = nameT.text.toString()
                            val phoneNumber = phoneNumberT.text.toString().toInt()
                            val address = addressT.text.toString()
                            var myRef: DatabaseReference =
                                FirebaseDatabase.getInstance().getReference("users")
                                    .child(firebaseUser.uid);
                            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNumber.toString()) && !TextUtils.isEmpty(
                                    address
                                )
                            ) {
                                val user: User =
                                    User(firebaseUser.uid, name,user.email ,user.password, phoneNumber, address, user.img)
                                myRef.setValue(user);
                                Toast.makeText(
                                    getApplicationContext(),
                                    "Profile Updated",
                                    Toast.LENGTH_LONG
                                ).show();
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "all fields are  required",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })




    }
}

//updating delivery

