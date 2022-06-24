package omt.aduc8386.haircutbookingapp.controller.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import omt.aduc8386.haircutbookingapp.controller.adapter.BarbershopSpinnerAdapter
import omt.aduc8386.haircutbookingapp.controller.adapter.FreeTimeSpinnerAdapter
import omt.aduc8386.haircutbookingapp.controller.adapter.ServiceSpinnerAdapter
import omt.aduc8386.haircutbookingapp.controller.adapter.StylistSpinnerAdapter
import omt.aduc8386.haircutbookingapp.databinding.ActivityBookingBinding
import omt.aduc8386.haircutbookingapp.model.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding

    private lateinit var barbershops: ArrayList<Barbershop>
    private lateinit var services: ArrayList<Service>
    private lateinit var stylists: ArrayList<Staff>
    private lateinit var freeTimes: ArrayList<String>

    private var user: User? = null
    private var barbershop: Barbershop? = null
    private var service: Service? = null
    private var stylist: Staff? = null
    private var bookingTime: String? = null

    private lateinit var barbershopsSpinner: Spinner
    private lateinit var servicesSpinner: Spinner
    private lateinit var stylistSpinner: Spinner
    private lateinit var freeTimeSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barbershopsSpinner = binding.spnChooseBarbershop
        servicesSpinner = binding.spnChooseService
        stylistSpinner = binding.spnChooseStylist
        freeTimeSpinner = binding.spnChooseTime
        getBarbershops()

        barbershopsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                barbershop = barbershops[position]
                getServices()
                getStylists()
                getFreeTimes()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                barbershopsSpinner.setSelection(0)
            }
        }

        servicesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                service = services[position]
                getStylists()
                getFreeTimes()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                servicesSpinner.setSelection(0)
            }
        }

        stylistSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                stylist = stylists[position]
                getFreeTimes()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                stylistSpinner.setSelection(0)
            }
        }

        freeTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bookingTime = freeTimes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                freeTimeSpinner.setSelection(0)
            }

        }

        binding.btnBook.setOnClickListener(View.OnClickListener {
            user = intent.getSerializableExtra(MainActivity.USER) as User

            val reference = FirebaseDatabase.getInstance().getReference("bookings")

            if (user != null && barbershop != null && stylist != null && bookingTime != null && service != null) {
                val booking =
                    BookingDetail(user!!, barbershop!!, service!!, stylist!!, bookingTime!!)
                reference.child(booking.id)
                    .setValue(booking, DatabaseReference.CompletionListener { error, ref ->
                        run {
                            val reference1 = FirebaseDatabase.getInstance()
                                .getReference("barbershops/${barbershop?.id}/services")

                            reference1.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (service in snapshot.children) {
                                        val serviceId = service.key
                                        reference1.child("$serviceId/staffs/${stylist?.id}/freeTimes")
                                            .addValueEventListener(object : ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    for (freeTime in snapshot.children) {
                                                        if (freeTime.getValue(String::class.java) == bookingTime) {
                                                            val freeTimeIndex = freeTime.key
                                                            reference1.child("$serviceId/staffs/${stylist?.id}/freeTimes/$freeTimeIndex")
                                                                .removeValue()
                                                        }
                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }

                                            })
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }
                    })
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BookingHistoryActivity::class.java)
                intent.putExtra(MainActivity.USER, user)
                startActivity(intent)
                finish()
            }
        })

        binding.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    private fun getFreeTimes() {
        val reference = FirebaseDatabase.getInstance()
            .getReference("barbershops/${barbershop?.id}/services/${service?.id}/staffs/${stylist?.id}/freeTimes")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                freeTimes = arrayListOf<String>()

                for (freeTime in snapshot.children) {
                    val freeTimeGet = freeTime.getValue(String::class.java)
                    freeTimes.add(freeTimeGet!!)
                }

                freeTimeSpinner.adapter = FreeTimeSpinnerAdapter(freeTimes)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getStylists() {
        val reference = FirebaseDatabase.getInstance()
            .getReference("barbershops/${barbershop?.id}/services/${service?.id}/staffs")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                stylists = arrayListOf<Staff>()

                for (stylist in snapshot.children) {
                    val stylistGet = stylist.getValue(Staff::class.java)
                    stylists.add(stylistGet!!)
                }

                stylistSpinner.adapter = StylistSpinnerAdapter(stylists)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getServices() {
        val reference =
            FirebaseDatabase.getInstance().getReference("barbershops/${barbershop?.id}/services")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                services = arrayListOf<Service>()

                for (service in snapshot.children) {
                    val serviceGet = service.getValue(Service::class.java)
                    services.add(serviceGet!!)
                }

                servicesSpinner.adapter = ServiceSpinnerAdapter(services)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getBarbershops() {
        val reference = FirebaseDatabase.getInstance().getReference("barbershops")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                barbershops = arrayListOf<Barbershop>()

                for (barbershop in snapshot.children) {
                    val barbershopGet = barbershop.getValue(Barbershop::class.java)
                    barbershops.add(barbershopGet!!)
                }
                barbershopsSpinner.adapter = BarbershopSpinnerAdapter(barbershops)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Get data failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

}