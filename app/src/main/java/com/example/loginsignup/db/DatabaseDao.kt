package com.example.loginsignup.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Insert
    fun registration(registration: Registration)

    @Query("SELECT * FROM registration ORDER BY id DESC")
    fun getalldate(): List<Registration>

    @Insert
    fun addMultipleData(vararg registration: Registration)

    @Query("DELETE FROM registration WHERE id = :id")
    fun deleteByTitle(id: Int?)

    @Query("SELECT * FROM registration WHERE email = :email")
    fun getemaildata(email: String): List<Registration>
}