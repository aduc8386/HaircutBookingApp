package omt.aduc8386.haircutbookingapp.controller.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import omt.aduc8386.haircutbookingapp.controller.adapter.BookingHistoryAdapter
import omt.aduc8386.haircutbookingapp.databinding.ActivityBookingHistoryBinding
import omt.aduc8386.haircutbookingapp.model.BookingDetail
import omt.aduc8386.haircutbookingapp.model.User

class BookingHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingHistoryBinding
    private lateinit var reference: DatabaseReference

    private lateinit var bookingHistoryRecyclerView: RecyclerView

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookingHistoryRecyclerView = binding.rcvBookingHistory
        bookingHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        reference = FirebaseDatabase.getInstance().getReference("bookings")
        user = intent.getSerializableExtra(MainActivity.USER) as User

        getBookingHistory()

        binding.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun getBookingHistory() {
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookings = arrayListOf<BookingDetail>()

                for(bookingDetail in  snapshot.children) {
                    val bookingDetailGot = bookingDetail.getValue(BookingDetail::class.java)
                    if(bookingDetailGot?.user?.id == user?.id) {
                        bookings.add(bookingDetailGot!!)
                    }
                }
                showBookingHistory(bookings)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Get data failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun showBookingHistory(bookings: ArrayList<BookingDetail>) {
        bookingHistoryRecyclerView.adapter = BookingHistoryAdapter(bookings)
    }
}