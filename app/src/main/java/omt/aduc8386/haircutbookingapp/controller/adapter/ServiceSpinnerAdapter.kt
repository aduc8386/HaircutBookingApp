package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.model.Service

class ServiceSpinnerAdapter(private val services: List<Service>) :BaseAdapter() {

    override fun getCount(): Int {
        return services.size
    }

    override fun getItem(position: Int): Any {
        return services[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_spinner_service, parent, false)

        val service = services[position]

        var tvService = view.findViewById<TextView>(R.id.tv_service)
        var tvPrice = view.findViewById<TextView>(R.id.tv_price)
        var ivPicture = view.findViewById<ImageView>(R.id.iv_service_picture)

        tvService.text = service.name
        tvPrice.text = String.format("%,.2f VND", service.price)

        Glide
            .with(ivPicture.context)
            .load(service.picture)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(ivPicture)

        return view
    }

}