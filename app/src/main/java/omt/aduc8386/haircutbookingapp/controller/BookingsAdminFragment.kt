package omt.aduc8386.haircutbookingapp.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.controller.activity.MainActivity
import omt.aduc8386.haircutbookingapp.controller.adapter.BookingListAdminAdapter
import omt.aduc8386.haircutbookingapp.databinding.FragmentBookingsAdminBinding
import omt.aduc8386.haircutbookingapp.model.BookingDetail
import omt.aduc8386.haircutbookingapp.model.User

class BookingsAdminFragment : Fragment(R.layout.fragment_bookings_admin) {

    companion object {
        fun newInstance(user: User): BookingsAdminFragment{
            val fragment = BookingsAdminFragment()
            val args = Bundle()
            args.putSerializable(MainActivity.USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentBookingsAdminBinding
    private lateinit var reference: DatabaseReference

    private lateinit var bookingsListAdminRecyclerView: RecyclerView

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingsAdminBinding.inflate(layoutInflater)
        user = arguments?.getSerializable(MainActivity.USER) as User
        bookingsListAdminRecyclerView = binding.rcvBookingListAdmin
        bookingsListAdminRecyclerView.layoutManager = LinearLayoutManager(context)
        reference = FirebaseDatabase.getInstance().getReference("bookings")
        getBookings()

        return binding.root
    }

    private fun getBookings() {
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookings = arrayListOf<BookingDetail>()

                for(bookingDetail in  snapshot.children) {
                    val bookingDetailGot = bookingDetail.getValue(BookingDetail::class.java)
                    if(bookingDetailGot?.barbershop?.id == user?.barbershopId) {
                        bookings.add(bookingDetailGot!!)
                        println(bookingDetailGot.toString())
                    }
                }
                bookingsListAdminRecyclerView.adapter = BookingListAdminAdapter(bookings)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Get data failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
