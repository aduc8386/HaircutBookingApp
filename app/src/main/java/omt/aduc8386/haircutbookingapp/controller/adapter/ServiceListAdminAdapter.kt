package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.model.Barbershop
import omt.aduc8386.haircutbookingapp.model.Service

class ServiceListAdminAdapter(private val serviceListAdmin: List<Service>) :
    RecyclerView.Adapter<ServiceListAdminAdapter.ServiceListAdminViewHolder>() {

    class ServiceListAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvServiceName: TextView = itemView.findViewById(R.id.tv_service)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val ivServicePicture: ImageView = itemView.findViewById(R.id.iv_service_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceListAdminViewHolder {
        val barbershopView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_service_list_admin, parent, false)
        return ServiceListAdminViewHolder(barbershopView)
    }

    override fun onBindViewHolder(holder: ServiceListAdminViewHolder, position: Int) {
        val service = serviceListAdmin[position]

        holder.tvServiceName.text = service.name
        holder.tvPrice.text = String.format("Price: %,.2f VND", service.price)

        Glide
            .with(holder.ivServicePicture.context)
            .load(service.picture)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(holder.ivServicePicture)
    }

    override fun getItemCount(): Int {
        return serviceListAdmin.size
    }
}