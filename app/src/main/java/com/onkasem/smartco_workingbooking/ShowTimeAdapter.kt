package com.onkasem.smartco_workingbooking

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_mainpage.view.*
import kotlinx.android.synthetic.main.tablebookingtimeformate.view.*
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ShowTimeAdapter(private val context: Context
                      , var showList: ArrayList<ShowTime>) : RecyclerView.Adapter<ShowTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.tablebookingtimeformate, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")
        fun bind(showTime: ShowTime) {
            itemView.apply {
                try{
                    val timestamp = showTime.bookingTime
                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val netDate = Date(milliseconds)
                    val date = sdf.format(netDate).toString()
                    Log.d("TAG170", date)

                    itemView.showBookingDate.text = date
                    itemView.hourBooking.text = showTime.amountHours.toString()
                } catch (e:Exception){
                    Log.wtf("show", e)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = showList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        showList.sortWith(compareBy({it.bookingTime}, {it.amountHours}))
        Log.d("showList",showList.toString())
        holder.bind(showList[position])

    }

    fun updateValue(shows: ArrayList<ShowTime>) {
        this.showList = shows
    }
}