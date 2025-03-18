package com.example.vitalslog.data.repository

import com.example.vitalslog.data.database.VitalsDao
import com.example.vitalslog.data.model.Vitals
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VitalsRepository @Inject constructor(
    private val vitalsDao: VitalsDao
) {
    suspend fun getAllVitals(): Flow<List<Vitals>> = vitalsDao.getAllVitals()

    suspend fun insertVitals(vitals: Vitals) {
        vitalsDao.insertVitals(vitals)
    }
}