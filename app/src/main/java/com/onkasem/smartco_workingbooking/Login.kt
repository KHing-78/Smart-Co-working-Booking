package com.onkasem.smartco_workingbooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast


class Login : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "LoginPage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Chack Authen
        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            Log.d(TAG, "Cotinue with: " + mAuth!!.currentUser!!.email)
            startActivity(Intent(this@Login, DashBoard::class.java))
            finish()
        }

        //Login
        loginButton.setOnClickListener {
            val email = emailLogin.text.toString().trim { it <= ' ' }
            val password = passwordLogin.text.toString().trim{ it <= ' ' }

            if (email.isEmpty()){
                toast("Please enter your email address.")
                Log.d(TAG,"Emai was empty")
                return@setOnClickListener
            }
            if (password.isEmpty()){
                toast("Please enter your password.")
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if (password.length < 6){
                        emailLogin.error = "Please Check your password. Password must have minimum 6 characters."
                        Log.d(TAG, "Enter password less than 6 characters")
                    }else {
                        toast("Authentication Faild: " + task.exception)
                        Log.d(TAG, "Autentication Failed: " + task.exception)
                    }
                } else {
                    toast("Sign in successfully!")
                    Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this@Login, DashBoard::class.java))
                    finish()
                }
            }
        }
        signupButton.setOnClickListener { startActivity(Intent(this@Login, Register::class.java))
            toast("Register")}
    }
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) {
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//        }
//    }
}
