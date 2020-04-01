package com.onkasem.smartco_workingbooking
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Booking (
    var id : String? = "",
    var place_name: String? = "",
    var Table_num: Int = 0,
    var free: Int = 0,
    var all: Int = 0
) : Parcelable