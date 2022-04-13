package com.example.loginsignup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.loginsignup.databinding.ActivityMainBinding
import com.example.loginsignup.db.RegistrationDatabase
import com.example.loginsignup.utils.AESEncyption

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val emailId = intent.getStringExtra("email").toString()

        val userdata = RegistrationDatabase(applicationContext).getNoteDao().getemaildata(emailId)
        userdata.forEach {
            binding.fname.text = it.first_name
            binding.lname.text = it.last_name
            binding.eMail.text = it.email
            binding.uname.text = it.userId
            binding.upassword.text = AESEncyption.decrypt(it.password).toString()
        }
    }
}