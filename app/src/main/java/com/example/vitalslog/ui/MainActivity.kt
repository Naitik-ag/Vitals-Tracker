package com.example.vitalslog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.vitalslog.ui.screen.VitalsLogApp
import com.example.vitalslog.ui.theme.VitalsLogTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.vitalslog.ui.viewmodel.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitalsLogTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VitalsLogApp()
                }
            }
        }
    }
}

