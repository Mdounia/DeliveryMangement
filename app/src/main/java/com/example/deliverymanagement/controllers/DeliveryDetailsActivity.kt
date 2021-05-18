package com.example.deliverymanagement.controllers

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*



class DeliveryDetailsActivity : AppCompatActivity() {



    lateinit var description : TextView
    lateinit var height: TextView
    lateinit var width: TextView
    lateinit var lenght : TextView
    lateinit var weight : TextView
    lateinit var deliveryAddress: TextView
    lateinit var sourceAddress: TextView
    lateinit var deliveryTime : TextView
    lateinit var deliveryDate : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_details)

        description=findViewById(R.id.desD)
        height=findViewById(R.id.heightD)
        width=findViewById(R.id.widthD)
        lenght=findViewById(R.id.lenghtD)
        weight=findViewById(R.id.weightD)
        deliveryAddress=findViewById(R.id.addrD)
        sourceAddress=findViewById(R.id.sourceD)
        deliveryTime=findViewById(R.id.timeD)
        deliveryDate=findViewById(R.id.dateD)
        val heightD=intent.getFloatExtra("deliveryHeight",4f)
        val widthD=intent.getFloatExtra("deliveryWidth",4f)
        val lenghtD=intent.getFloatExtra("deliveryLenght",4f)
        val weightD=intent.getFloatExtra("deliveryWeight",4f)
        height.setText(heightD.toString())
        width.setText(widthD.toString())
        lenght.setText(lenghtD.toString())
        weight.setText(weightD.toString())
        description.setText(intent.getStringExtra("deliveryDes").toString())
        deliveryAddress.setText(intent.getStringExtra("deliveryAddr").toString())
        sourceAddress.setText(intent.getStringExtra("deliverySource").toString())
        deliveryTime.setText(intent.getStringExtra("deliveryDate").toString())
        deliveryDate.setText(intent.getStringExtra("deliveryTime").toString())










    }
}