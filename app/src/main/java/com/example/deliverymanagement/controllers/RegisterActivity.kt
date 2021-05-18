package com.example.deliverymanagement.controllers
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var phoneNumber : EditText
    lateinit var address : EditText
    lateinit var add: Button
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var myRef: DatabaseReference





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth= FirebaseAuth.getInstance()
        name=findViewById(R.id.des1)
        email=findViewById(R.id.email_lg)
        password=findViewById(R.id.password)
        phoneNumber=findViewById(R.id.phoneNumber)
        address=findViewById(R.id.address)
        add=findViewById(R.id.signIn)

        add.setOnClickListener {
            val name = name.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val phoneNumber = phoneNumber.text.toString().toInt()
            val address = address.text.toString()
            if(TextUtils.isEmpty(name) ||TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber.toString()) ||TextUtils.isEmpty(address) ){
                Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_LONG).show();
            }else{
                register(name,email,password,phoneNumber,address)
            }
        }
    }

    private fun register(name: String, email: String, password: String, phoneNumber: Int, address: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
            OnCompleteListener {
                if(it.isSuccessful){
                    val fireBaseuser: FirebaseUser? =firebaseAuth.currentUser
                    val userId:String = fireBaseuser!!.uid
                    myRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
                    var user= User(userId,name,email,password,phoneNumber,address,"Default")
                    myRef.setValue(user).addOnCompleteListener {
                        if(it.isSuccessful){
                            val source = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(source)
                        }else{
                            Toast.makeText(applicationContext, "error!!!", Toast.LENGTH_LONG).show()
                        }
                    }

                }else{
                    Toast.makeText(applicationContext, it.exception?.message, Toast.LENGTH_LONG).show()

                }
            })
    }
}
