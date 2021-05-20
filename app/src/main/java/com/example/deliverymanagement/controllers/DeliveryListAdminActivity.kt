package com.example.deliverymanagement.controllers

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DeliveryListAdminActivity : AppCompatActivity() {
    lateinit var listDel: ListView
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    private lateinit var deliveries: ArrayList<Delivery>
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    var fmt: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_list_admin)
        listDel = findViewById(R.id.lv_deliveries)
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("deliveries")
        deliveries = ArrayList()
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser= firebaseAuth.currentUser!!

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                deliveries.clear()
                for (postSnapshot in dataSnapshot.children) {
                    //getting delivery
                    val td: Map<String, Object> = postSnapshot.getValue() as HashMap<String, Object>
                    val delivery= Delivery(
                        td.get("delId").toString(),
                        td.get("description").toString(),
                        td.get("height").toString().toFloat(),
                        td.get("width").toString().toFloat(),
                        td.get("lenght").toString().toFloat(),
                        td.get("weight").toString().toFloat(),
                        td.get("deliveryAddress").toString(),
                        td.get("sourceAddress").toString(),
                        td.get("deliveryDate") .toString()  ,
                        td.get("deliveryTime").toString(),
                        td.get("userId").toString(),

                        )

                        deliveries.add(delivery)
                        val adapter = DeliveryListActivity.CustomizedAdapter(applicationContext, deliveries)
                        listDel.adapter = adapter

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        listDel.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                val delivery: Delivery = deliveries[i]
                showUpdateDeleteDialog(
                    delivery.delId,
                    delivery.description,
                    delivery.height,
                    delivery.width,
                    delivery.lenght,
                    delivery.weight,
                    delivery.deliveryAddress,
                    delivery.sourceAddress,
                    delivery.deliveryDate,
                    delivery.deliveryTime,
                    firebaseUser.uid

                )
                true
            }
        listDel.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext,"Delivery clicked : "+deliveries[position].description,
                Toast.LENGTH_LONG).show()
            intent = Intent(applicationContext,DeliveryDetailsActivity::class.java)
            intent.putExtra("deliveryDes",deliveries[position].description)
            intent.putExtra("deliveryHeight", deliveries[position].height)
            intent.putExtra("deliveryWidth",deliveries[position].width)
            intent.putExtra("deliveryLenght",deliveries[position].lenght)
            intent.putExtra("deliveryWeight",deliveries[position].weight)
            intent.putExtra("deliveryAddr",deliveries[position].deliveryAddress)
            intent.putExtra("deliverySource",deliveries[position].sourceAddress)
            intent.putExtra("deliveryDate",deliveries[position].deliveryDate)
            intent.putExtra("deliveryTime",deliveries[position].deliveryTime)
            startActivity(intent)
        }
    }
    class CustomizedAdapter(private val myContext: Context, private val deliveries: List<Delivery>) :
        ArrayAdapter<Delivery>(myContext, 0, deliveries){
        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val layout= LayoutInflater.from(myContext).inflate(R.layout.row_delivery, null)
            val textViewDes = layout.findViewById<View>(R.id.del_des) as TextView
            val textViewAdd = layout.findViewById<View>(R.id.del_address) as TextView
            val textViewDate = layout.findViewById<View>(R.id.del_date) as TextView
            val delivery: Delivery =deliveries.get(position)
            textViewDes.setText(delivery.description);
            textViewAdd.setText(delivery.deliveryAddress);
            textViewDate.setText(delivery.deliveryDate.toString());
            return layout

        }

    }
    private fun deleteDelivery(delId :String ): Boolean {
        //getting the specified delivery reference
        var myRef: DatabaseReference =FirebaseDatabase.getInstance().getReference("deliveries").child( delId);
        //deleting delivery
        myRef.removeValue()
        Toast.makeText(getApplicationContext(), "delivery deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private fun showUpdateDeleteDialog(
            delId :String,
            description : String,
            height: Float,
            width: Float,
            lenght: Float,
            weight: Float,
            deliveryAddress : String,
            sourceAddress : String,
            deliveryDate :String,
            deliveryTime : String,
            userId:String
    ) {
        val dateAdd: Date =fmt.parse(deliveryDate)
        if(System.currentTimeMillis() > dateAdd.getTime()){
            Toast.makeText(applicationContext, "you cant delete this delivery its on going ", Toast.LENGTH_LONG).show();
        }else{

            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView: View = inflater.inflate(R.layout.delete_delivery_dialog, null)
            dialogBuilder.setView(dialogView)
            val editTextDes = dialogView.findViewById<View>(R.id.desD) as TextView
            val editTextHeight= dialogView.findViewById<View>(R.id.heightD) as TextView
            val editTextWidth = dialogView.findViewById<View>(R.id.widthD) as TextView
            val editTextLenght =dialogView.findViewById<View>(R.id.lenghtD) as TextView
            val editTextWeight = dialogView.findViewById<View>(R.id.weightD) as TextView
            val editTextDelAdd= dialogView.findViewById<View>(R.id.delivery_addressD) as TextView
            val editTextSourceAdd = dialogView.findViewById<View>(R.id.source_addressD) as TextView
            val editTextDelDate =dialogView.findViewById<View>(R.id.delivery_dateD) as TextView
            val editTextDelTime = dialogView.findViewById<View>(R.id.delivery_timeD) as TextView
            val buttonDelete: Button = dialogView.findViewById<View>(R.id.delivery_deleteD) as Button
            dialogBuilder.setTitle(description)
            editTextDes.setText(description)
            editTextHeight.setText(height.toString())
            editTextWidth.setText(width.toString())
            editTextLenght.setText(lenght.toString())
            editTextWeight.setText(weight.toString())
            editTextDelAdd.setText(deliveryAddress.toString())
            editTextSourceAdd.setText(sourceAddress.toString())
            editTextDelDate.setText(deliveryDate.toString())
            editTextDelTime.setText(deliveryTime.toString())
            val alertDialog: AlertDialog = dialogBuilder.create()
            alertDialog.show()
            buttonDelete.setOnClickListener(View.OnClickListener {
                deleteDelivery(delId)
                alertDialog.dismiss()

            })
        }

    }
}