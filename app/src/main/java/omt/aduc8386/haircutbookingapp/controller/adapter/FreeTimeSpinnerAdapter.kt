package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R

class FreeTimeSpinnerAdapter(private val freeTimes: List<String>) :BaseAdapter() {

    override fun getCount(): Int {
        return freeTimes.size
    }

    override fun getItem(position: Int): Any {
        return freeTimes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_spinner_free_time, parent, false)

        val freeTime = freeTimes[position]

        var tvFreeTime = view.findViewById<TextView>(R.id.tv_free_time)

        tvFreeTime.text = freeTime

        return view
    }

}