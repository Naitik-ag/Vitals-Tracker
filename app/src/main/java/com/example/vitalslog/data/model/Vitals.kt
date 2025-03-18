package com.example.vitalslog.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class Vitals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val systolic: String,
    val diastolic: String,
    val heartRate: String,
    val weight: String,
    val babyKicks: String,
    val timestamp: Long = System.currentTimeMillis()
)

