package com.example.vitalslog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vitalslog.data.model.Vitals

@Database(entities = [Vitals::class], version = 2, exportSchema = false)
abstract class VitalsDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
}
