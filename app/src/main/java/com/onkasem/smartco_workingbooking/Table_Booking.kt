package com.onkasem.smartco_workingbooking

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.onkasem.smartco_workingbooking.DashBoard.Companion.INTENT_PARCELABLE
import java.time.LocalDate
import java.util.*


class Table_Booking : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table__booking)

        val db = FirebaseFirestore.getInstance()

        val ShowDateLocal : CardView = findViewById(R.id.ShowDateCard)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val localDate: TextView = findViewById(R.id.Localdate)

        val person : EditText = findViewById(R.id.edit_peopleCount)
        val joinButt : Button = findViewById(R.id.join_switch)
        val time : TimePicker = findViewById(R.id.timePicker1)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date: LocalDate = LocalDate.now()
            localDate.setText("$date")

            ShowDateLocal.setOnClickListener {
                val bookingDateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    localDate.setText("${year}-${month+1}-${dayOfMonth}")
                }, year, month, day)
                bookingDateDialog.show()
            }
        }

        val placeBook = intent.getParcelableExtra<Booking>(DashBoard.INTENT_PARCELABLE)
        val place_name = findViewById<TextView>(R.id.place_name)
        val TableOrder = findViewById<TextView>(R.id.TableOrder)

        place_name.text = placeBook.place_name
        TableOrder.text = placeBook.Table_num.toString()

        Log.d("documentId" , placeBook.id)

    }

    fun updateData () {
        // If you're using custom Kotlin objects in Android, add an @ServerTimestamp
        // annotation to a Date field for your custom object classes. This indicates
        // that the Date field should be treated as a server timestamp by the object mapper.
        val docRef = db.collection("Place").document("some-id")

        // Update the timestamp field with the value from the server
        val updates = hashMapOf<String, Any>(
            "timestamp" to FieldValue.serverTimestamp()
        )

        docRef.update(updates).addOnCompleteListener { }

    }

}
