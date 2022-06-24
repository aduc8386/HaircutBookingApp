package omt.aduc8386.haircutbookingapp.controller.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import omt.aduc8386.haircutbookingapp.databinding.ActivityLoginBinding
import omt.aduc8386.haircutbookingapp.databinding.ActivitySignupBinding
import omt.aduc8386.haircutbookingapp.model.User

class SignupActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener(View.OnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            val phoneNumber = binding.edtPhoneNumber.text.toString().trim()

            if(email.isEmpty()) {
                Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(password.isEmpty()) {
                Toast.makeText(this, "Password can not be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(confirmPassword != password) {
                Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(name.isEmpty()) {
                Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(phoneNumber.isEmpty()) {
                Toast.makeText(this, "Phone number can not be empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val user = User(email, password, name, phoneNumber)

            signUp(user);

        })

        binding.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun signUp(user: User) {
        val reference = FirebaseDatabase.getInstance().getReference("users")

        reference.child(user.id).setValue(user, DatabaseReference.CompletionListener { error, ref ->
            run {
                Toast.makeText(baseContext, "Sign up success", Toast.LENGTH_SHORT).show()
                Toast.makeText(baseContext, "Log in to continue", Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        })

    }


}