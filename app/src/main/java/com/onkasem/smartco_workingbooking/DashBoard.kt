package com.onkasem.smartco_workingbooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dash_board.*
import org.jetbrains.anko.toast

class DashBoard : AppCompatActivity() {

    lateinit var mDb: FirebaseFirestore

    lateinit var books: ArrayList<Booking>

    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        mDb = FirebaseFirestore.getInstance()
        books = ArrayList()
        val actionBar = supportActionBar
        actionBar!!.title = "Home"

        mDb.collection("Place").get().addOnSuccessListener { documents ->
            Log.d("TEST", "${documents.size()}")
            for (document in documents) {
                Log.d("TEST", "${document.id} => ${document.data}")

                val book = document.toObject(Booking::class.java)
                book.id = document.id
                Log.wtf("test" , book.toString())
                books.add(book)

                mainRecycleView.adapter = BookAdapter(this, books){
                    val intent = Intent(this, tableBookingTime::class.java)

                    intent.putExtra(INTENT_PARCELABLE, it)
                    startActivity(intent)
                    toast("click")
                }


            }
            mainRecycleView.layoutManager = LinearLayoutManager(this)

        }.addOnFailureListener { task->
            Log.d("testTask", task.message)
        }

        val viewCurrentBookingBtn: Button = findViewById(R.id.viewCurrentBookingBtn)
        viewCurrentBookingBtn.setOnClickListener {
            val myIntent = Intent(baseContext, BookingDescription::class.java)
            startActivity(myIntent)
        }
        
    }

}
