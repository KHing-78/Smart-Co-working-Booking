package com.onkasem.smartco_workingbooking

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.time.LocalDate
import java.util.*

class Table_Booking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table__booking)


        val ShowTimeLocal : CardView = findViewById(R.id.ShowDateCard)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val localDate: TextView = findViewById(R.id.Localdate)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date: LocalDate = LocalDate.now()
            localDate.setText("$date")

            ShowTimeLocal.setOnClickListener {
                val bookingDateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    localDate.setText("${year}-${month+1}-${dayOfMonth}")
                }, year, month, day)
                bookingDateDialog.show()
            }
        }
    }

}
