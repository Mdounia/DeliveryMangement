package com.example.deliverymanagement.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.deliverymanagement.R

class AdminActivity : AppCompatActivity() {

    lateinit var users: Button
    lateinit var deliveries: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        users=findViewById(R.id.users_btn)
        deliveries=findViewById(R.id.deliveries_btn)


        users.setOnClickListener {
            val source = Intent(applicationContext, UsersListActivity::class.java)
            startActivity(source)
        }
    }
}