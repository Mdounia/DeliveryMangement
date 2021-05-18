package com.example.deliverymanagement.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.deliverymanagement.R

class MainActivity : AppCompatActivity() {
    lateinit var add_del: ImageButton
    lateinit var delList: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_del=findViewById(R.id.add_del)
        delList=findViewById(R.id.delivery_btn)
        add_del.setOnClickListener {
            val source = Intent(applicationContext, DeliveryFormActivity::class.java)
            startActivity(source)

        }
        delList.setOnClickListener {
            val source = Intent(applicationContext, DeliveryListActivity::class.java)
            startActivity(source)
        }




    }
}