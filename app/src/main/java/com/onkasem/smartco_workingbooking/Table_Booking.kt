package com.onkasem.smartco_workingbooking

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class Table_Booking : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var  addBooking: ArrayList<ShowTime>

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

        val cancelButton: Button = findViewById(R.id.CancelBtn)
        val confirmButton: Button = findViewById(R.id.ConfirmBtn)


        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd")
        localDate.setText(dateInString)

        ShowDateLocal.setOnClickListener {
            val bookingDateDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    localDate.setText("${year}/${month + 1}/${dayOfMonth}")
                },
                year,
                month,
                day
            )
            bookingDateDialog.show()
        }
        val hourSelectDropdown: Spinner = findViewById(R.id.hourSelectDropdown)
        var selectedHours: Int = 0
        val amountHourString: String = hourSelectDropdown.onItemSelectedListener.toString()
        selectedHours = when (amountHourString) {
            "1" -> 1
            "2" -> 2
            "3" -> 3
            else -> 0
        }

        val placeBook = intent.getParcelableExtra<Booking>(DashBoard.INTENT_PARCELABLE)
        val place_name = findViewById<TextView>(R.id.place_name)
        val TableOrder = findViewById<TextView>(R.id.TableOrder)

        place_name.text = placeBook.place_name
        TableOrder.text = "Table: ${placeBook.Table_num.toString()}"

        Log.d("documentId", "55555555555555" + placeBook.id)

//        addBooking.add(ShowTime("","",selectedHours,
//            Timestamp(year,month),person.text.toString().toInt(),placeBook.Table_num.toString()))

        //confiram Butt
        confirmButton.setOnClickListener {
            // If you're using custom Kotlin objects in Android, add an @ServerTimestamp
            // annotation to a Date field for your custom object classes. This indicates
            // that the Date field should be treated as a server timestamp by the object mapper.
            val docRef = db.collection("Coe_coworking_space")
            docRef.add(addBooking).addOnSuccessListener { documentReference ->
                    Log.d("TableBooking", "DocumentSnapshot added with ID: " + documentReference.id)
                }
                .addOnFailureListener { e ->
                    Log.w("TableBooking", "Error adding document", e)
                }

            cancelButton.setOnClickListener {
                toast("Booking is cancel")
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
