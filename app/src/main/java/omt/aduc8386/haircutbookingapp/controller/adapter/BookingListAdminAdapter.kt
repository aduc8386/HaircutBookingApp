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

class BookingListAdminAdapter(private val bookingListAdmin: List<BookingDetail>) :
    RecyclerView.Adapter<BookingListAdminAdapter.BookingListAdminViewHolder>() {

    class BookingListAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCustomerName: TextView = itemView.findViewById(R.id.tv_customer_name)
        val tvPhoneNumber: TextView = itemView.findViewById(R.id.tv_phone_number)
        val tvService: TextView = itemView.findViewById(R.id.tv_service)
        val tvServicePrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvBookingTime: TextView = itemView.findViewById(R.id.tv_booking)
        val tvStylist: TextView = itemView.findViewById(R.id.tv_stylist_name)
        val ivCustomerAvatar: ImageView = itemView.findViewById(R.id.iv_customer_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListAdminViewHolder {
        val barbershopView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_booking_list_admin, parent, false)
        return BookingListAdminViewHolder(barbershopView)
    }

    override fun onBindViewHolder(holder: BookingListAdminViewHolder, position: Int) {
        val bookingDetail = bookingListAdmin[position]

        holder.tvBookingTime.text = String.format("Booking time: %s", bookingDetail.bookTime)
        holder.tvCustomerName.text = bookingDetail.user.name
        holder.tvPhoneNumber.text = String.format("Phone number: %s", bookingDetail.user.phoneNumber)
        holder.tvService.text = String.format("Service: %s", bookingDetail.service.name)
        holder.tvServicePrice.text = String.format("Cost: %,.2f VND", bookingDetail.service.price)
        holder.tvStylist.text = String.format("Stylist: %s", bookingDetail.stylist.name)

        Glide
            .with(holder.ivCustomerAvatar.context)
            .load(bookingDetail.user.avatar)
            .centerCrop()
            .error(R.drawable.ic_baseline_person_24)
            .into(holder.ivCustomerAvatar)
    }

    override fun getItemCount(): Int {
        return bookingListAdmin.size
    }
}