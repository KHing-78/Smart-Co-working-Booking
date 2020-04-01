package com.onkasem.smartco_workingbooking

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.booking_list.view.*
import kotlin.collections.ArrayList

class BookAdepter(private val context: Context
                  , val bookList: ArrayList<Booking>
                  ,val listener:(Booking) -> Unit) : RecyclerView.Adapter<BookAdepter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_list, parent, false)
        return  ViewHolder(view)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(booking: Booking, listener: (Booking) -> Unit) {
            itemView.apply {
                try{
                    itemView.place_header.text = booking.place_name
                    itemView.place_discription.text = "Table: ${booking.Table_num.toString()}"
                    itemView.freeTableStatus.text = "Free/All: ${booking.free} / ${booking.all}"
                }catch (e:Exception){
                    Log.wtf("test" , e)
                }
            }
            itemView.setOnClickListener { listener(booking) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bookList.sortWith(compareBy({it.place_name}, {it.Table_num}))
        Log.d("booklist",bookList.toString())
        holder.bind(bookList[position], listener)

        }
}