package com.example.deliverymanagement.controllers

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.User
import com.google.firebase.database.*


class UsersListActivity : AppCompatActivity() {

    lateinit var listUsers : ListView
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    private lateinit var users: ArrayList<User>

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
                TODO("Not yet implemented")
            }
        })




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


}