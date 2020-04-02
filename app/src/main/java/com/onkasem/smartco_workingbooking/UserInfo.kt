package com.onkasem.smartco_workingbooking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class UserInfo : AppCompatActivity() {
    lateinit var mUser: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        mUser = FirebaseAuth.getInstance()
//        getUser()
        val displayName : TextView = findViewById(R.id.displayName)
        val displayEmail : TextView = findViewById(R.id.displayEmail)
        //displayName.text(userName)
        //displayEmail.text(email)

    }
//    private fun getUser() {
//        var currentUser = mUser.currentUser
//        currentUser?.let{
//            val userName = currentUser.displayName
//            val email = currentUser.email
//            val uid = currentUser.uid
//        }
//        return
//    }
}