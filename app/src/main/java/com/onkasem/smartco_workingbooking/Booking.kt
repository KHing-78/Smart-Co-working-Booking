package com.onkasem.smartco_workingbooking
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now

data class Booking (
    var name_place: String = "",
    var discription: String = "",
    var booking_time: Timestamp = now() )
