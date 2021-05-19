package com.example.deliverymanagement.controllers
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.deliverymanagement.R
import com.example.deliverymanagement.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var signup: Button
    lateinit var signin: Button
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var fgtPass:TextView
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email=findViewById(R.id.email_lg)
        password=findViewById(R.id.pwd)
        signup=findViewById(R.id.signUp)
        signin=findViewById(R.id.signIn)
        fgtPass=findViewById(R.id.fgtPass)
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users")
        firebaseAuth= FirebaseAuth.getInstance()

        signup.setOnClickListener {
            val source = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(source)

        }
        signin.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext, "all fields required", Toast.LENGTH_LONG).show()
            }else{
                login(email,password)
            }
        }

        fgtPass.setOnClickListener {
            val source = Intent(applicationContext, ForgetPasswordActivity::class.java)
            startActivity(source)
        }

    }

    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                if(email.equals("admin@gmail.com") && password.equals("admin123456")){
                    val source = Intent(applicationContext, AdminActivity::class.java)
                    startActivity(source)
                    finish()
                }else{
                    val source = Intent(applicationContext, MainActivity::class.java)
                    startActivity(source)
                    finish()
                }

            }else{
                Toast.makeText(applicationContext, it.exception?.message, Toast.LENGTH_LONG).show()

            }
        }
    }


}

