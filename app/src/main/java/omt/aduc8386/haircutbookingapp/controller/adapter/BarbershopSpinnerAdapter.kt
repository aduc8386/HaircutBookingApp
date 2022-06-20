package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.model.Barbershop

class BarbershopSpinnerAdapter(private val barbershops: List<Barbershop>) :BaseAdapter() {

    override fun getCount(): Int {
        return barbershops.size
    }

    override fun getItem(position: Int): Any {
        return barbershops[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_spinner_barbershop, parent, false)

        val barbershop = barbershops[position]

        var tvName = view.findViewById<TextView>(R.id.tv_barbershop)
        var tvAddress = view.findViewById<TextView>(R.id.tv_address)
        var ivAvatar = view.findViewById<ImageView>(R.id.iv_barbershop_avatar)

        tvName.text = barbershop.name
        tvAddress.text = barbershop.address

        Glide
            .with(ivAvatar.context)
            .load(barbershop.avatar)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(ivAvatar)

        return view
    }

}