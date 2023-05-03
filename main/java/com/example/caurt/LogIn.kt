package com.example.caurt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail :TextView
    private lateinit var edtPassword :TextView
    private lateinit var btnLogin :Button
    private lateinit var btnSignUp :Button
    lateinit var save: Button
    private lateinit var mAuth : FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    val Email="emailKey"
    val Pass="passKey"
    val myfile = "myprefile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)
        btnSignUp = findViewById(R.id.signup)
        save=findViewById(R.id.save)
        sharedPreferences=getSharedPreferences(myfile, Context.MODE_PRIVATE)
        btnSignUp.setOnClickListener {
            val intentA = Intent(this, SignUp::class.java)
            finish()
            startActivity(intentA)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password);
        }

    }
    fun save(v: View){
        val e=edtEmail.text.toString()
        val p=edtPassword.text.toString()

        val editor=sharedPreferences.edit()

        editor.putString(Email,e)
        editor.putString(Pass,p)

        editor.apply()
    }

    fun clear(v: View){

        edtEmail.text=""
        edtPassword.text=""

    }
    fun get(v: View){
        sharedPreferences=getSharedPreferences(myfile,Context.MODE_PRIVATE)

        edtEmail.text=sharedPreferences.getString(Email,"")
        edtPassword.text = sharedPreferences.getString(Pass,"")

    }
    private fun login(email: String, password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    startActivity(intent)

                } else {
                        Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                    }
            }
    }
}