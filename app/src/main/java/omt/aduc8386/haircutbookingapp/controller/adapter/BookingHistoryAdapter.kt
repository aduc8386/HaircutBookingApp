package omt.aduc8386.haircutbookingapp.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.model.BookingDetail

class BookingHistoryAdapter(private val bookingHistoryList: List<BookingDetail>) :
    RecyclerView.Adapter<BookingHistoryAdapter.BookingHistoryViewHolder>() {

    class BookingHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBarbershop: TextView = itemView.findViewById(R.id.tv_barbershop)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        val tvService: TextView = itemView.findViewById(R.id.tv_service)
        val tvServicePrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvBookingTime: TextView = itemView.findViewById(R.id.tv_booking)
        val tvStylist: TextView = itemView.findViewById(R.id.tv_stylist_name)
        val ivBarbershopAvatar: ImageView = itemView.findViewById(R.id.iv_barbershop_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val barbershopView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_booking_history, parent, false)
        return BookingHistoryViewHolder(barbershopView)
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        val bookingDetail = bookingHistoryList[position]

        holder.tvBookingTime.text = String.format("Booking time: %s", bookingDetail.bookTime)
        holder.tvBarbershop.text = bookingDetail.barbershop.name
        holder.tvAddress.text = String.format("Address: %s", bookingDetail.barbershop.address)
        holder.tvService.text = String.format("Service: %s", bookingDetail.service.name)
        holder.tvServicePrice.text = String.format("Cost: %,.2f VND", bookingDetail.service.price)
        holder.tvStylist.text = String.format("Stylist: %s", bookingDetail.stylist.name)

        Glide
            .with(holder.ivBarbershopAvatar.context)
            .load(bookingDetail.barbershop.avatar)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(holder.ivBarbershopAvatar)
    }

    override fun getItemCount(): Int {
        return bookingHistoryList.size
    }
}