package com.example.loginsignup.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Registration(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val userId: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}