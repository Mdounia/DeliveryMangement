package com.example.deliverymanagement.controllers

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class DeliveryFormActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference


    lateinit var description : EditText
    lateinit var height: EditText
    lateinit var width: EditText
    lateinit var lenght : EditText
    lateinit var weight : EditText
    lateinit var deliveryAddress: EditText
    lateinit var sourceAddress: EditText
    lateinit var deliveryTime : EditText
    lateinit var deliveryDate : EditText
    lateinit var addDel : Button
    lateinit var cancel: Button
    lateinit var back: FloatingActionButton
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    var fmt: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_form)
        description=findViewById(R.id.des)
        height=findViewById(R.id.height)
        width=findViewById(R.id.width)
        lenght=findViewById(R.id.lenght)
        weight=findViewById(R.id.weight)
        deliveryAddress=findViewById(R.id.delivery_address)
        sourceAddress=findViewById(R.id.source_address)
        deliveryTime=findViewById(R.id.delivery_time)
        deliveryDate=findViewById(R.id.delivery_date)
        addDel=findViewById(R.id.add_delivery)
        cancel=findViewById(R.id.cancel)
        back=findViewById(R.id.back)
        database = FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser= firebaseAuth.currentUser!!
        myRef = database.getReference("deliveries")
        var id : String = myRef.push().key as String
            addDel.setOnClickListener {
                if(TextUtils.isEmpty(description.text.toString()) || TextUtils.isEmpty(height.text.toString()) || TextUtils.isEmpty(width.text.toString())
                        || TextUtils.isEmpty(lenght.text.toString()) || TextUtils.isEmpty(weight.text.toString()) || TextUtils.isEmpty(deliveryAddress.text.toString())
                        || TextUtils.isEmpty(sourceAddress.text.toString())
                        || TextUtils.isEmpty(deliveryDate.text.toString())|| TextUtils.isEmpty(deliveryTime.text.toString())) {
                    Toast.makeText(applicationContext, "all fields are required", Toast.LENGTH_LONG).show()

                }
                else{
                    val d = Delivery(
                            id,
                            description.text.toString(),
                            height.text.toString().toFloat(),
                            width.text.toString().toFloat(),
                            lenght.text.toString().toFloat(),
                            weight.text.toString().toFloat(),
                            deliveryAddress.text.toString(),
                            sourceAddress.text.toString(),
                            deliveryDate.text.toString(),
                            deliveryTime.text.toString(),
                            firebaseUser.uid
                    )
                    val dateAdd:Date=fmt.parse(deliveryDate.text.toString())
                    if(System.currentTimeMillis() > dateAdd.getTime()){
                        Toast.makeText(applicationContext, "this day is impossible  ,choose another day", Toast.LENGTH_LONG).show();
                    }else{
                    myRef.child((id)).setValue(d).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                        var intent = Intent(applicationContext, DeliveryListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    }
                    }
                }


        }
    cancel.setOnClickListener {
        description.setText("")
        height.setText("")
        width.setText("")
        lenght.setText("")
        weight.setText("")
        deliveryAddress.setText("")
        sourceAddress.setText("")
        deliveryTime.setText("")
        deliveryDate.setText("")
    }
        back.setOnClickListener {
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}