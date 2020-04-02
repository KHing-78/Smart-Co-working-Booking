package com.onkasem.smartco_workingbooking

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.booking_list.view.*
import kotlin.collections.ArrayList

class BookAdapter(private val context: Context
                  , var bookList: ArrayList<Booking>
                  , val listener:(Booking) -> Unit) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_list, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount() = bookList.size

    fun updateValue(books: ArrayList<Booking>) {
        this.bookList = books
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(booking: Booking, listener: (Booking) -> Unit) {
            itemView.apply {
                try{
                    itemView.time_header.text = booking.place_name
                    itemView.tableOrder.text = "Table: ${booking.Table_num.toString()}"
                    itemView.showBookingDate.text = "Free/All: ${booking.free} / ${booking.all}"
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bookList.sortWith(compareBy({it.place_name}, {it.Table_num}))
        Log.d("booklist",bookList.toString())
        holder.bind(bookList[position], listener)

    }

}