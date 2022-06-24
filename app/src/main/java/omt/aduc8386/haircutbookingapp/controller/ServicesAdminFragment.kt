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
import omt.aduc8386.haircutbookingapp.controller.adapter.ServiceListAdminAdapter
import omt.aduc8386.haircutbookingapp.databinding.FragmentServicesAdminBinding
import omt.aduc8386.haircutbookingapp.model.Barbershop
import omt.aduc8386.haircutbookingapp.model.Service
import omt.aduc8386.haircutbookingapp.model.User

class ServicesAdminFragment: Fragment(R.layout.fragment_services_admin) {

    companion object {
        fun newInstance(user: User): ServicesAdminFragment{
            val fragment = ServicesAdminFragment()
            val args = Bundle()
            args.putSerializable(MainActivity.USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    private var user: User? = null
    private lateinit var binding: FragmentServicesAdminBinding
    private lateinit var reference: DatabaseReference
    private lateinit var serviceListAdminRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesAdminBinding.inflate(layoutInflater)
        serviceListAdminRecyclerView = binding.rcvServiceListAdmin
        serviceListAdminRecyclerView.layoutManager = LinearLayoutManager(context)
        user = arguments?.getSerializable(MainActivity.USER) as User

        getServices(user?.barbershopId)


        return binding.root
    }

    private fun getServices(id: String?) {
        reference = FirebaseDatabase.getInstance().getReference("barbershops/${id}/services")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val services = arrayListOf<Service>()
                for(child1 in snapshot.children) {
                    services.add(child1.getValue(Service::class.java)!!)
                }
                serviceListAdminRecyclerView.adapter = ServiceListAdminAdapter(services)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

}