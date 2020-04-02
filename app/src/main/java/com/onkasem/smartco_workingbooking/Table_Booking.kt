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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.onkasem.smartco_workingbooking.DashBoard.Companion.INTENT_PARCELABLE
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_booking_description.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class Table_Booking : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

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
        TableOrder.text = placeBook.Table_num.toString()

        Log.d("documentId", "55555555555555" + placeBook.id)

        //confiram Butt

        confirmButton.setOnClickListener {
            // If you're using custom Kotlin objects in Android, add an @ServerTimestamp
            // annotation to a Date field for your custom object classes. This indicates
            // that the Date field should be treated as a server timestamp by the object mapper.
            val docRef = db.collection("Place").document("${placeBook.id}")
            if (person.text.toString().toInt() <= placeBook.all) {
                docRef.update("free", person.text.toString().toInt())

            } else {
                toast("Please enter in range")
            }

            cancelBtn.setOnClickListener {

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
