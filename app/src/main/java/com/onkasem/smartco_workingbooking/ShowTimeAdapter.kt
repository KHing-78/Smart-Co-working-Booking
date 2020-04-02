package com.onkasem.smartco_workingbooking

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_mainpage.view.*
import kotlinx.android.synthetic.main.tablebookingtimeformate.view.*


class ShowTimeAdapter(private val context: Context
                      , var showList: ArrayList<ShowTime>) : RecyclerView.Adapter<ShowTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.tablebookingtimeformate, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(showTime: ShowTime) {
            itemView.apply {
                try{
                    itemView.showBookingDate.text = showTime.bookingTime.toString()
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