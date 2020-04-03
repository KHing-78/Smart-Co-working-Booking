package com.onkasem.smartco_workingbooking

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set


class Table_Booking : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    val  addBooking = ArrayList<addBooks>()

    data class addBooks(val uid:String, val amountHours:Int, val date:String, val time: String, val peopleCount: Int, val tableOrder: String)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table__booking)



        val db = FirebaseFirestore.getInstance()

        val ShowDateLocal: CardView = findViewById(R.id.ShowDateCard)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val localDate: TextView = findViewById(R.id.localDate)

        val person: EditText = findViewById(R.id.edit_peopleCount)
        val joinButt: Button = findViewById(R.id.join_switch)
        val time: TimePicker = findViewById(R.id.timePicker1)


        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd")
        localDate.setText(dateInString)

        var stringTime : String = ""

        var bookingDateDialog : DatePickerDialog

        ShowDateLocal.setOnClickListener {
                bookingDateDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    localDate.setText("${year}/${month + 1}/${dayOfMonth}")
                    stringTime = "${year}/${month + 1}/${dayOfMonth}"
                    Log.d("time", stringTime)
                },
                year,
                month,
                day
            )
            bookingDateDialog.show()
        }

        val hourSelectDropdown: Spinner = findViewById(R.id.hourSelectDropdown)
//        var selectedHours: Int = 0
//        val amountHourString: String = hourSelectDropdown.onItemSelectedListener.toString()
//        selectedHours = when (amountHourString) {
//            "1" -> 1
//            "2" -> 2
//            "3" -> 3
//            else -> 0
//        }
        val amountHour = hourSelectDropdown.selectedItem.toString().toInt()

        val placeBook = intent.getParcelableExtra<Booking>(DashBoard.INTENT_PARCELABLE)
        val place_name = findViewById<TextView>(R.id.place_name)
        val TableOrder = findViewById<TextView>(R.id.TableOrder)

        place_name.text = placeBook.place_name
        TableOrder.text = "Table: ${placeBook.Table_num.toString()}"

        Log.d("documentId", "55555555555555" + placeBook.id)

        //confiram Butt
        confirmButton.setOnClickListener {
            // If you're using custom Kotlin objects in Android, add an @ServerTimestamp
            // annotation to a Date field for your custom object classes. This indicates
            // that the Date field should be treated as a server timestamp by the object mapper.
            var uid : String = ""
            val docRef = db.collection("Coe_coworking_space")


            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                uid = user.uid
                addBooking.add(addBooks(uid, amountHour,
                    stringTime, "${time.hour}.${time.minute}",person.text.toString().toInt(),placeBook.Table_num.toString()))
                Log.d("add", addBooking.toString())


            cancelButton.setOnClickListener {
                toast("Booking is cancel")

            cancelBtn.setOnClickListener {



            }
            val updateMap: MutableMap<String, Any> = HashMap()
            updateMap["uid"] = addBooking[0]
            updateMap["amountHours"] = addBooking[1]
            updateMap["date"] = addBooking[2]
            updateMap["time"] = addBooking[3]
            updateMap["peopleCount"] = addBooking[4]
            updateMap["tableOrder"] = addBooking[5]

            docRef.add(updateMap).addOnSuccessListener { documentReference ->
                Log.d("TableBooking", "DocumentSnapshot added with ID: " + documentReference.id)

            }
                .addOnFailureListener { e ->
                    Log.w("fail", "Error adding document", e)
                }
        }
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


}
