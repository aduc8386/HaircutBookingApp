package omt.aduc8386.haircutbookingapp.controller.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import omt.aduc8386.haircutbookingapp.R
import omt.aduc8386.haircutbookingapp.controller.BookingsAdminFragment
import omt.aduc8386.haircutbookingapp.controller.InfoAdminFragment
import omt.aduc8386.haircutbookingapp.controller.ServicesAdminFragment
import omt.aduc8386.haircutbookingapp.databinding.ActivityMainAdminBinding
import omt.aduc8386.haircutbookingapp.model.User

class MainAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainAdminBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawer
        navigationView = binding.navView
        actionBarToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_nav_drawer,
            R.string.close_nav_drawer
        )
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra(MainActivity.USER) as User

        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_name).text = user?.name
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_email).text = user?.email

        val avatar = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.civ_avatar)

        Glide.with(avatar.context)
            .load(user?.avatar)
            .centerCrop()
            .error(R.drawable.ic_image_load_failed)
            .into(avatar)

        loadFragment(BookingsAdminFragment.newInstance(user!!))
        navigationView.setCheckedItem(R.id.ic_bookings)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_bookings -> {
                    loadFragment(BookingsAdminFragment.newInstance(user!!))
                }
                R.id.ic_info -> {
                    loadFragment(InfoAdminFragment.newInstance(user!!))
                }
                R.id.ic_service -> {
                    loadFragment(ServicesAdminFragment.newInstance(user!!))
                }
                R.id.ic_log_out -> {
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        binding.appBar.ivMenuIcon.setOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarToggle.onOptionsItemSelected((item))) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, fragment)
            .commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}