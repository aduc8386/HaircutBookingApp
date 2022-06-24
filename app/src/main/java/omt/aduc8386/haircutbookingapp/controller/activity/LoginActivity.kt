package omt.aduc8386.haircutbookingapp.controller.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import omt.aduc8386.haircutbookingapp.databinding.ActivityLoginBinding
import omt.aduc8386.haircutbookingapp.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var email: String = binding.edtEmail.text.toString();
            var password: String = binding.edtPassword.text.toString();

            if (email.trim().isEmpty()) {
                Toast.makeText(baseContext, "Email can't be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (password.trim().isEmpty()) {
                Toast.makeText(baseContext, "Password can't be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            accountCheck(email, password)
        })

        binding.btnSignup.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        })
    }

    private fun accountCheck(email: String, password: String) {
        val reference = FirebaseDatabase.getInstance().getReference("users")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (user in snapshot.children) {
                    val userGot = user.getValue(User::class.java)

                    if (userGot!!.email == email && userGot.password == password && userGot.barbershopId == null) {
                        val intent = Intent(baseContext, MainActivity::class.java)
                        intent.putExtra(MainActivity.USER, userGot)
                        startActivity(intent)
                        finish()
                        return
                    }
                    else if(userGot!!.email == email && userGot.password == password && userGot.barbershopId != null) {
                        val intent = Intent(baseContext, MainAdminActivity::class.java)
                        intent.putExtra(MainActivity.USER, userGot)
                        startActivity(intent)
                        finish()
                        return
                    }
                }

                Toast.makeText(baseContext, "Incorrect email or password", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }


}