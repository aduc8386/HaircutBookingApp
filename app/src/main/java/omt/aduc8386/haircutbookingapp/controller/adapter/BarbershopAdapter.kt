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

class BarbershopAdapter(private val barbershops: List<Barbershop>) :
    RecyclerView.Adapter<BarbershopAdapter.BarbershopViewHolder>() {

    class BarbershopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_barbershop)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_barbershop_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarbershopViewHolder {
        val barbershopView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_barbershop, parent, false)
        return BarbershopViewHolder(barbershopView)
    }

    override fun onBindViewHolder(holder: BarbershopViewHolder, position: Int) {
        val barbershop = barbershops[position]

        holder.tvName.text = barbershop.name
        holder.tvAddress.text = barbershop.address

        Glide
            .with(holder.ivAvatar.context)
            .load(barbershop.avatar)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(holder.ivAvatar)
    }

    override fun getItemCount(): Int {
        return barbershops.size
    }
}