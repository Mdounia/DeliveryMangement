package com.example.deliverymanagement.controllers

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.Delivery
import com.example.deliverymanagement.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class UsersListActivity : AppCompatActivity() {

    lateinit var listUsers : ListView
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    private lateinit var users: ArrayList<User>
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        listUsers = findViewById(R.id.lv_users)
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users")
        users = ArrayList()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val td: Map<String, Object> = postSnapshot.getValue() as HashMap<String, Object>
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
                val adapter = CustomizedAdapter(applicationContext, users)
                //adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, users)
                listUsers.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        listUsers.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                val user: User = users[i]
                showUpdateDeleteDialog(
                    user.userId,
                    user.name,
                    user.email.toString(),
                    user.phoneNumber,
                    user.address
                )
                true
            }




    }


    class CustomizedAdapter(private val myContext: Context, private val users: List<User>) :
        ArrayAdapter<User>(myContext, 0, users){
        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val layout= LayoutInflater.from(myContext).inflate(R.layout.row_user, null)
            val textViewName = layout.findViewById<View>(R.id.user_fullName) as TextView
            val textViewEmail = layout.findViewById<View>(R.id.user_email) as TextView
            val user:User = users.get(position)
            textViewName.setText(user.name);
            textViewEmail.setText(user.email);
            return layout

        }

    }
    private fun deleteUser(userId :String ): Boolean {
         lateinit var deliveries: ArrayList<Delivery>
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser= firebaseAuth.currentUser!!
        //getting the specified delivery reference
        var myRef: DatabaseReference =FirebaseDatabase.getInstance().getReference("users").child( userId);
        var myRefDel: DatabaseReference =FirebaseDatabase.getInstance().getReference("deliveries")
        deliveries= ArrayList()
        myRefDel.addValueEventListener(object : ValueEventListener {
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
                    if(delivery.userId==firebaseUser.uid){
                        deliveries.add(delivery)
                    }
                }
                if(deliveries.isNullOrEmpty()) {
                    //deleting delivery
                    myRef.removeValue()
                    Toast.makeText(getApplicationContext(), "user deleted", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "You cant delete this user ,he still has deliveries", Toast.LENGTH_LONG).show();
                }

            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    return true
    }


    private fun showUpdateDeleteDialog(
        userId :String,
        name : String,
        email: String,
        phoneNumber: Int,
        address : String,
    ) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView: View = inflater.inflate(R.layout.update_delete_dialog, null)
            dialogBuilder.setView(dialogView)
            val nameText = dialogView.findViewById<View>(R.id.name) as TextView
            val emailText= dialogView.findViewById<View>(R.id.email) as TextView
            val phoneNumberText = dialogView.findViewById<View>(R.id.phoneNumber) as TextView
            val addressText =dialogView.findViewById<View>(R.id.address)as TextView
            val buttonDelete: Button = dialogView.findViewById<View>(R.id.user_delete) as Button
            dialogBuilder.setTitle(name)
            nameText.setText(name)
            emailText.setText(email)
            phoneNumberText.setText(phoneNumber.toString())
            addressText.setText(address)
            val alertDialog: AlertDialog = dialogBuilder.create()
            alertDialog.show()
            buttonDelete.setOnClickListener(View.OnClickListener {
                deleteUser(userId)
                alertDialog.dismiss()

            })
        }

}