package com.onkasem.smartco_workingbooking
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now

data class Booking (
    var place_name: String = "",
    var Table_num: Int = 0,
    var free: Int = 0,
    var all: Int = 0
)
