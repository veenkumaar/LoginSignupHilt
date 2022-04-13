package com.example.loginsignup.activity.signup

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginsignup.db.Registration
import com.example.loginsignup.db.RegistrationDatabase
import com.example.loginsignup.utils.AESEncyption
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {
    val firstname: MutableLiveData<String> = MutableLiveData()
    val lastname: MutableLiveData<String> = MutableLiveData()
    val emailadd: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirm_password: MutableLiveData<String> = MutableLiveData()

    val _firstname: LiveData<String> get() = firstname
    val _lastname: LiveData<String> get() = lastname
    val _email: LiveData<String> get() = emailadd
    val _password: LiveData<String> get() = password
    val _confirm_password: LiveData<String> get() = confirm_password

    fun onSubmit(
        applicationContext: Context,
        first: String,
        last: String,
        mail: String,
        pass: String,
        userID: String?
    ) {
        val encprytpassword = AESEncyption.encrypt(pass)
        RegistrationDatabase(applicationContext).getNoteDao().registration(
            Registration(
                first,
                last,
                mail,
                encprytpassword.toString(),
                userID.toString()
            )
        )
    }

    @Throws(Exception::class)
    fun createTransactionID(): String? {
        return UUID.randomUUID().toString().replace("-", "").uppercase(Locale.getDefault())
    }

    fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}