package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.model.Staff

class StylistSpinnerAdapter(private val stylists: List<Staff>) :BaseAdapter() {

    override fun getCount(): Int {
        return stylists.size
    }

    override fun getItem(position: Int): Any {
        return stylists[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_spinner_stylist, parent, false)

        val stylist = stylists[position]

        var tvStylist = view.findViewById<TextView>(R.id.tv_stylist_name)
        var tvPhoneNumber = view.findViewById<TextView>(R.id.tv_phone_number)
        var ivAvatar = view.findViewById<ImageView>(R.id.iv_stylist_avatar)

        tvStylist.text = stylist.name
        tvPhoneNumber.text = String.format("%s", stylist.phoneNumber)

        Glide
            .with(ivAvatar.context)
            .load(stylist.avatar)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(ivAvatar)

        return view
    }

}