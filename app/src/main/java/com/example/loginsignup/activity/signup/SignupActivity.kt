package com.example.loginsignup.activity.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignup.R
import com.example.loginsignup.activity.login.LoginActivity
import com.example.loginsignup.databinding.ActivitySignupBinding
import com.example.loginsignup.db.RegistrationDatabase
import dagger.hilt.android.AndroidEntryPoint
import dagger.internal.DaggerGenerated
import javax.inject.Inject
import kotlin.math.sign

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    val signupViewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.signup = signupViewModel

        val userID = signupViewModel.createTransactionID()

        binding.btLogiin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btSubmit.setOnClickListener {
            if (TextUtils.isEmpty(binding.etFirstname.text.toString().trim())) {
                binding.etFirstname.error = "Please enter first name"
            } else if (TextUtils.isEmpty(binding.etLastname.text.toString().trim())) {
                binding.etLastname.error = "Please enter last name"
            } else if (TextUtils.isEmpty(binding.etEmail.text.toString().trim())) {
                binding.etEmail.error = "Please enter email"
            } else if (TextUtils.isEmpty(binding.etPassword.text.toString().trim())) {
                binding.etPassword.error = "Please enter password"
            } else if (TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim())) {
                binding.etConfirmPassword.error = "Please enter confirm password"
            } else if (binding.etPassword.text.toString()
                    .trim() != binding.etConfirmPassword.text.toString().trim()
            ) {
                Toast.makeText(applicationContext, "Please check your password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val checkemail =
                    signupViewModel.isValidEmail(binding.etEmail.text.toString().trim())
                if (!checkemail) {
                    Toast.makeText(
                        applicationContext,
                        "Please enter correct email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val checkpassword =
                        signupViewModel.isValidPassword(binding.etPassword.text.toString().trim())
                    if (!checkpassword) {
                        Toast.makeText(
                            applicationContext,
                            "Minimum Password 8 Character",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val findEmail = RegistrationDatabase(applicationContext).getNoteDao()
                            .getemaildata(binding.etEmail.text.toString().trim())
                        if (findEmail.isEmpty()) {
                            signupViewModel.onSubmit(
                                applicationContext,
                                binding.etFirstname.text.toString(),
                                binding.etLastname.text.toString(),
                                binding.etEmail.text.toString(),
                                binding.etConfirmPassword.text.toString(),
                                userID
                            )
                            startActivity(Intent(this, LoginActivity::class.java)
                                .apply {
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                                })
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "User Already Registered",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}