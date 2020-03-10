package com.onkasem.smartco_workingbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_dash_board.*
import org.jetbrains.anko.doAsync
import java.util.concurrent.Delayed

class DashBoard : AppCompatActivity() {
    var bAdapter : BookAdepter? = null

    lateinit var mDb: FirebaseFirestore

    lateinit var books: ArrayList<Booking>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        mDb = FirebaseFirestore.getInstance()
        books = ArrayList()
        getData()
        val actionBar = supportActionBar
        actionBar!!.title = "Home"
    }

    fun getData () {

        mDb.collection("Place").get().addOnSuccessListener { documents ->
            Log.d("TEST", "${documents.size()}")
            for (document in documents) {
                Log.d("TEST", "${document.id} => ${document.data}")

                val book = document.toObject(Booking::class.java)

                Log.wtf("test" , book.toString())
                books.add(book)
            }
            mainRecycleView.layoutManager = LinearLayoutManager(this)
            bAdapter = BookAdepter(books)
            mainRecycleView.adapter = bAdapter
        }.addOnFailureListener { task->
            Log.d("test", task.message)
        }
    }

}
