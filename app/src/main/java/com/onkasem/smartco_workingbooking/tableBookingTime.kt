package com.onkasem.smartco_workingbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_table_booking_time.*
import kotlinx.android.synthetic.main.booking_list.*
import org.jetbrains.anko.toast

class tableBookingTime : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    lateinit var  shows: ArrayList<ShowTime>

    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_booking_time)

        val placeBook = intent.getParcelableExtra<Booking>(DashBoard.INTENT_PARCELABLE)
        val addButton : Button = findViewById(R.id.addButton)

        db = FirebaseFirestore.getInstance()
        shows = ArrayList()
        val actionBar = supportActionBar
        actionBar!!.title = placeBook.place_name

        placeHaeader_T.text = placeBook.place_name

        db.collection("Coe_coworking_space").get().addOnSuccessListener { documents ->
            Log.d("show", "${documents.size()} ${placeBook.Table_num}")
            val showTimeAdapter = ShowTimeAdapter(this, shows)

            for (document in documents) {
                val show = document.toObject(ShowTime::class.java)
                show.id = document.id
                Log.wtf("test" , show.toString())
                shows.add(show)

                showTimeAdapter.updateValue(shows)
                showTimeAdapter.notifyDataSetChanged()
            }
            showRecyclerView.adapter = showTimeAdapter
            showRecyclerView.layoutManager = LinearLayoutManager(this)


        }.addOnFailureListener { task->
            Log.d("testTask", task.message)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, Table_Booking::class.java)
            intent.putExtra(tableBookingTime.INTENT_PARCELABLE, placeBook)
            startActivity(intent)
        }
    }
}