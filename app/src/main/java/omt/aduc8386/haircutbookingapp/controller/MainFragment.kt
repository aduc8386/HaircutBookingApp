package omt.aduc8386.haircutbookingapp.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.controller.activity.BarbershopsActivity
import omt.aduc8386.haircutbookingapp.controller.activity.BookingActivity
import omt.aduc8386.haircutbookingapp.controller.activity.BookingHistoryActivity
import omt.aduc8386.haircutbookingapp.controller.activity.MainActivity
import omt.aduc8386.haircutbookingapp.databinding.FragmentMainBinding
import omt.aduc8386.haircutbookingapp.model.User

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance(user: User): MainFragment{
            val fragment = MainFragment()
            val args = Bundle()
            args.putSerializable(MainActivity.USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentMainBinding

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        user = arguments?.getSerializable(MainActivity.USER) as User
        binding.tvUsername.text = user?.name

        binding.btnBook.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, BookingActivity::class.java)
            Log.d("TAG", "onViewCreated: clicked")
            intent.putExtra(MainActivity.USER, user)
            startActivity(intent)
        })

        binding.btnBarbershop.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, BarbershopsActivity::class.java)
            startActivity(intent)
        })

        binding.btnHistory.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, BookingHistoryActivity::class.java)
            intent.putExtra(MainActivity.USER, user)
            startActivity(intent)
        })


        return binding.root
    }
}
