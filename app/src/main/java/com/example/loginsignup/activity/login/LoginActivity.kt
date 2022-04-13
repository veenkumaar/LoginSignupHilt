package com.example.loginsignup.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.loginsignup.MainActivity
import com.example.loginsignup.R
import com.example.loginsignup.databinding.ActivityLoginBinding
import com.example.loginsignup.db.RegistrationDatabase
import com.example.loginsignup.utils.AESEncyption

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var getEmail = ""
    private var getPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.loginUsername.text.toString().trim())) {
                binding.loginUsername.error = "Please enter your email"
            } else if (TextUtils.isEmpty(binding.loginPassword.text.toString().trim())) {
                binding.loginPassword.error = "Please enter your password"
            } else {
                val logindata = RegistrationDatabase(applicationContext).getNoteDao()
                    .getemaildata(binding.loginUsername.text.toString().trim())
                logindata.forEach {
                    getEmail = it.email
                    getPassword = AESEncyption.decrypt(it.password).toString()
                }
                if (binding.loginUsername.text.toString() == getEmail) {
                    if (binding.loginPassword.text.toString() == getPassword) {
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, MainActivity::class.java)
                            .apply {
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                                putExtra("email", binding.loginUsername.text.toString())
                            })
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Please check your password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please check your credential",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}