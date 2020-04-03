package com.onkasem.smartco_workingbooking

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShowTime(
    var id: String? = "",
    var uid: String? = "",
    var amountHours: Int = 0,
    var date:String = "",
    var time: String = "",
    var peopleCount: Int = 0,
    var tableOrder: String = ""

) : Parcelable