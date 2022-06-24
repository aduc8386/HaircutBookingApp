package omt.aduc8386.haircutbookingapp.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.controller.activity.MainActivity
import omt.aduc8386.haircutbookingapp.databinding.FragmentBookingsAdminBinding
import omt.aduc8386.haircutbookingapp.databinding.FragmentInfoAdminBinding
import omt.aduc8386.haircutbookingapp.model.Barbershop
import omt.aduc8386.haircutbookingapp.model.User

class InfoAdminFragment: Fragment(R.layout.fragment_info_admin) {

    companion object {
        fun newInstance(user: User): InfoAdminFragment{
            val fragment = InfoAdminFragment()
            val args = Bundle()
            args.putSerializable(MainActivity.USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    private var user: User? = null
    private var barbershop: Barbershop? = null
    private lateinit var binding: FragmentInfoAdminBinding
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoAdminBinding.inflate(layoutInflater)
        user = arguments?.getSerializable(MainActivity.USER) as User
        reference = FirebaseDatabase.getInstance().getReference("barbershops")

        getBarbershopInfo(user?.barbershopId)

        binding.btnSaveInfo.setOnClickListener(View.OnClickListener {
            val name = binding.edtBarbershopName.text.toString()
            val address = binding.edtBarbershopAddress.text.toString()
            val phoneNumber = binding.edtBarbershopPhoneNumber.text.toString()

            reference.child("${barbershop!!.id}/name").setValue(name)
            reference.child("${barbershop!!.id}/address").setValue(address)
            reference.child("${barbershop!!.id}/phoneNumber").setValue(phoneNumber)

            Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    private fun getBarbershopInfo(barbershopId: String?) {
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(child in snapshot.children){
                    val barbershopGot = child.getValue(Barbershop::class.java)
                    if(barbershopGot?.id == barbershopId) {
                        barbershop = barbershopGot
                        binding.edtBarbershopName.setText(barbershopGot?.name)
                        binding.edtBarbershopAddress.setText(barbershopGot?.address)
                        binding.edtBarbershopPhoneNumber.setText(barbershopGot?.phoneNumber)

                        Glide.with(binding.civBarbershopAvatar.context)
                            .load(barbershopGot?.avatar)
                            .centerCrop()
                            .error(R.drawable.ic_image_load_failed)
                            .into(binding.civBarbershopAvatar)


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}