package omt.aduc8386.haircutbookingapp.controller.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import omt.aduc8386.haircutbookingapp.databinding.ActivityMainBinding
import omt.aduc8386.haircutbookingapp.model.User

class MainActivity : AppCompatActivity() {

    companion object {
        const val USER = "USER"
    }

    private var user: User? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserName()

        binding.btnBook.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra(USER, user)
            startActivity(intent)
        })

        binding.btnBarbershop.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, BarbershopsActivity::class.java)
            startActivity(intent)
        })

        binding.btnHistory.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, BookingHistoryActivity::class.java)
            startActivity(intent)
        })
    }

    private fun getUserName() {
        user = intent.getSerializableExtra(USER) as User

        binding.tvUsername.text = user?.name
    }
}