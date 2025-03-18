package com.example.vitalslog.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.vitalslog.ReminderWorker
import com.example.vitalslog.data.model.Vitals
import com.example.vitalslog.data.repository.VitalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vitalsRepository: VitalsRepository,
    application: Application
) : AndroidViewModel(application)
{

    private val workManager = WorkManager.getInstance(application)

    private val _vitals = MutableStateFlow<List<Vitals>>(emptyList())
    val vitals: StateFlow<List<Vitals>> = _vitals.asStateFlow()

    init {
        loadVitals()
        scheduleVitalsReminder()
    }

    private fun loadVitals() {
        viewModelScope.launch {
            vitalsRepository.getAllVitals()
                .catch { throwable ->
                    _vitals.value = emptyList()
                }
                .collect { vitalsList ->
                    withContext(Dispatchers.Main) {
                        _vitals.value = vitalsList
                    }
                }
        }
    }

    fun addVitals(vitals: Vitals) {
        viewModelScope.launch {
            vitalsRepository.insertVitals(vitals)
        }
    }

    private fun scheduleVitalsReminder() {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(5, TimeUnit.HOURS)
            .setInitialDelay(5, TimeUnit.HOURS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "VitalsReminderWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
