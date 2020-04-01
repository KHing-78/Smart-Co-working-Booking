package com.onkasem.smartco_workingbooking

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast


class Register : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private  val TAG: String = "Register"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@Register, DashBoard::class.java))
            finish()
        }

        registerButton.setOnClickListener {
            val username = usernameRegister.text.toString().trim(){ it <= ' '}
            val password = passwordRegister.text.toString().trim(){ it <= ' ' }
            val confirmPassWord = confirmPasswordRegister.text.toString().trim(){ it <= ' ' }
            val email = emailRegister.text.toString().trim(){ it <= ' ' }

            if (username.isEmpty()){
                toast("Please enter your username.")
                return@setOnClickListener
            }
            if (password.isEmpty()){
                toast("Please enter your password.")
                return@setOnClickListener
            }
            if (confirmPassWord.isEmpty()){
                toast("Please enter your confirmPassWord.")
                return@setOnClickListener
            }
            if (confirmPassWord != password){
                toast("Your password is not match.")
                return@setOnClickListener
            }
            if (email.isEmpty()){
                toast("Please enter your email.")
                return@setOnClickListener
            }

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if (password.length < 6 ){
                        toast("Password too short! Please enter minimum 6 characters")
                    } else {
                        toast("Authentication Failed: " + task.exception)
                    }
                }else {
                    toast("Create account successfully!")
                    startActivity(Intent(this@Register, DashBoard::class.java))
                    finish()
                }
            }
        }
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