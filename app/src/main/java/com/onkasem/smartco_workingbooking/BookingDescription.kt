package com.onkasem.smartco_workingbooking

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_booking_description.*
import java.text.SimpleDateFormat
import java.util.*


class BookingDescription : AppCompatActivity() {
    lateinit var bookingPlace: TextView
    lateinit var bookingDate: TextView
    lateinit var tableNo: TextView
    lateinit var personInReserve: TextView
    lateinit var qrCodeDescription: TextView
    lateinit var qrCodeScanButton: TextView
    lateinit var mapViewButton: TextView
    lateinit var cancelBookingButton: TextView
    lateinit var countDownTime: TextView
    private var amountHours: Long = 0
    private lateinit var date: String
    private lateinit var time: String


    private var mStartTimeInMillis: Long = 0
    private var mTimeLeftInMillis: Long = 0
    private var mEndTime: Long = 0
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning = false

    lateinit var mDb: FirebaseFirestore
    lateinit var mUser: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_description)

        bookingPlace = findViewById(R.id.bookingPlace)
        bookingDate = findViewById(R.id.showBookingDate)
        tableNo = findViewById(R.id.tableNo)
        personInReserve = findViewById(R.id.personInReserve)
        qrCodeDescription = findViewById(R.id.qrCode_description)
        qrCodeScanButton = findViewById(R.id.qrCodeScanButton)
        mapViewButton = findViewById(R.id.mapViewButton)
        cancelBookingButton = findViewById(R.id.cancelBookingButton)
        countDownTime = findViewById(R.id.countDownTime)

        mDb = FirebaseFirestore.getInstance()
        getData()

        val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        val currentDate = getCurrentDateTime()
        val currentDateInString = currentDate.toString("yyyy/MM/dd hh:mm:ss")
        //val bookingDateTimestamp = dateFormat.format(date+time)

        //val endBooking = LocalDateTime.parse(bookingDateTimestamp, DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT)).plusHours(amountHours)


            qrCodeScanButton.setOnClickListener {
                qrCodeDescription.setVisibility(View.INVISIBLE)
                qrCodeScanButton.setVisibility(View.INVISIBLE)
                mapViewButton.setVisibility(View.INVISIBLE)
                countDownTime.setVisibility(View.VISIBLE)

                setTime(amountHours * 3600000);
                startTimer();

            }
            cancelBookingButton.setOnClickListener {

                resetTimer()

                // Initialize a new layout inflater instance
                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.popup_layout,null)

                // Initialize a new instance of popup window
                val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    popupWindow.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.RIGHT
                    popupWindow.exitTransition = slideOut

                }

                // Get the widgets reference from custom view
                val cancelButtonPopup = view.findViewById<Button>(R.id.cancel_popup_button)
                val okButtonPopup = view.findViewById<Button>(R.id.ok_popup_button)

                // Set click listener for popup window's text view

                // Set a click listener for popup's button widget
                cancelButtonPopup.setOnClickListener{
                    // Dismiss the popup window
                    popupWindow.dismiss()
                }
                okButtonPopup.setOnClickListener{
                    // Dismiss the popup window
                    popupWindow.dismiss()
                    val myIntent = Intent(baseContext, DashBoard::class.java)
                    startActivity(myIntent)
                }

                // Set a dismiss listener for popup window
                popupWindow.setOnDismissListener {
                    Toast.makeText(applicationContext,"Popup closed",Toast.LENGTH_SHORT).show()
                }


                // Finally, show the popup window on app
                TransitionManager.beginDelayedTransition(bookingDescriptionLayout)
                popupWindow.showAtLocation(
                    bookingDescriptionLayout, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )
            }



    }

    private fun getData() {
        mUser = FirebaseAuth.getInstance()
        var currentUser = mUser.currentUser
        val docRef = mDb.collection("Coe_coworking_space").whereEqualTo("uid",currentUser?.uid).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TEST", "${document.id} => ${document.data}")
                    //currentBooking.push(document.id.uid)
                    var place: String = "CoE Co-Working Space"
                    date = document.getString("date")!!
                    time = document.getString("time")!!
                    var peopleCount: Long? = document.getLong("peopleCount")
                    var tableOrder: String? = document.getString("tableOrder")
                    amountHours = document.getLong("amountHours")!!
                    bookingPlace.text = place
                    bookingDate.text = "$date $time"
                    personInReserve.text = "${peopleCount} people"
                    tableNo.text = "${tableOrder.toString()}"
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TEST", "Error getting documents.", exception)
            }
    }
    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    private fun setTime(milliseconds: Long) {
        mStartTimeInMillis = milliseconds
        resetTimer()
    }

    private fun startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                updateWatchInterface()
            }
        }.start()
        mTimerRunning = true
        updateWatchInterface()
    }

    private fun resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis
        updateCountDownText()

    }

    private fun updateCountDownText() {
        val hours = (mTimeLeftInMillis / 1000).toInt() / 3600
        val minutes = (mTimeLeftInMillis / 1000 % 3600).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String
        timeLeftFormatted = if (hours > 0) {
            java.lang.String.format(
                Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, seconds
            )
        } else {
            java.lang.String.format(
                Locale.getDefault(),
                "%02d:%02d", minutes, seconds
            )
        }
        countDownTime.setText(timeLeftFormatted)
    }


    override fun onStop() {
        super.onStop()
        val prefs =
            getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong("startTimeInMillis", mStartTimeInMillis)
        editor.putLong("millisLeft", mTimeLeftInMillis)
        editor.putBoolean("timerRunning", mTimerRunning)
        editor.putLong("endTime", mEndTime)
        editor.apply()
        mCountDownTimer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        val prefs =
            getSharedPreferences("prefs", Context.MODE_PRIVATE)
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000)
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis)
        mTimerRunning = prefs.getBoolean("timerRunning", false)
        updateCountDownText()
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0)
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0
                mTimerRunning = false
                updateCountDownText()
            } else {
                startTimer()
            }
        }
    }
    private fun updateWatchInterface() {

    }
}