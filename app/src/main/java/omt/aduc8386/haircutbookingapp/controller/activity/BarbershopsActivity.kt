package omt.aduc8386.haircutbookingapp.controller.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import omt.aduc8386.haircutbookingapp.controller.adapter.BarbershopAdapter
import omt.aduc8386.haircutbookingapp.databinding.ActivityBarbershopsBinding
import omt.aduc8386.haircutbookingapp.model.Barbershop

class BarbershopsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarbershopsBinding
    private lateinit var reference: DatabaseReference

    private lateinit var barbershopRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarbershopsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        barbershopRecyclerView = binding.rvBarbershops
        barbershopRecyclerView.layoutManager = LinearLayoutManager(this)
        reference = FirebaseDatabase.getInstance().getReference("barbershops")

        getBarbershops()

        binding.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun getBarbershops() {
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val barbershops = arrayListOf<Barbershop>()

                for(barbershop in  snapshot.children) {
                    val barbershopGot = barbershop.getValue(Barbershop::class.java)
                    barbershops.add(barbershopGot!!)
                }
                showBarbershops(barbershops)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Get data failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun showBarbershops(barbershops: ArrayList<Barbershop>) {
        barbershopRecyclerView.adapter = BarbershopAdapter(barbershops)
    }
}