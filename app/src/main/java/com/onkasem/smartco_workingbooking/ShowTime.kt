package com.onkasem.smartco_workingbooking

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShowTime(
    var id: String? = "",
    var uid: String? = "",
    var amountHours: Int = 0,
    var bookingTime: Timestamp = now() ,
    var peopleCount: Int = 0,
    var tableOrder: String = ""

) : Parcelable