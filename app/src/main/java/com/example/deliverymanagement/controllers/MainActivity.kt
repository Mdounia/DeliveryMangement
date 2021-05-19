package com.example.deliverymanagement.controllers

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.example.deliverymanagement.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var add_del: ImageButton
    lateinit var delList: Button
    lateinit var profil: Button
    lateinit var logout: Button
    lateinit var edit: ImageButton
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_del=findViewById(R.id.add_del)
        delList=findViewById(R.id.delivery_btn)
        profil=findViewById(R.id.profile_btn)
        edit=findViewById(R.id.edit_profil)
        logout=findViewById(R.id.logout)
        firebaseAuth= FirebaseAuth.getInstance()
        add_del.setOnClickListener {
            val source = Intent(applicationContext, DeliveryFormActivity::class.java)
            startActivity(source)

        }
        delList.setOnClickListener {
            val source = Intent(applicationContext, DeliveryListActivity::class.java)
            startActivity(source)
        }
        profil.setOnClickListener {
            val source = Intent(applicationContext, ProfilActivity::class.java)
            startActivity(source)
        }
        edit.setOnClickListener {
                val source = Intent(applicationContext, ProfileUpdateActivity::class.java)
                startActivity(source)
        }
        logout.setOnClickListener {
            firebaseAuth.signOut()
            val source = Intent(applicationContext, LoginActivity::class.java)
            startActivity(source)
            finish()
        }






    }

}



