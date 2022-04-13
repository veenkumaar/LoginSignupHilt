package com.example.loginsignup.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Registration::class],
    version = 1
)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun getNoteDao(): DatabaseDao

    companion object {
        @Volatile
        private var instance: RegistrationDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RegistrationDatabase::class.java,
            "loginsignup"
        ).allowMainThreadQueries().build()
    }
}