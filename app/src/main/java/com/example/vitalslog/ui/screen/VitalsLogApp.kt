package com.example.vitalslog.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vitalslog.data.model.Vitals
import com.example.vitalslog.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsLogApp(
    homeViewModel: HomeViewModel = hiltViewModel()
){
    var showDialog by remember { mutableStateOf(false) }
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = { VitalsLogAppBar(scrollBehaviour = scrollBehaviour) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF9C4DB9),
                contentColor = Color.White,
                modifier = Modifier.clip(RoundedCornerShape(60.dp))
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vitals" )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Color(0xFFC8ADFC),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    ) {contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ){
            HomeScreen(homeViewModel)

            if (showDialog) {
                AddVitalsDialog(
                    onDismiss = { showDialog = false },
                    onSubmit = { vitals ->
                        homeViewModel.addVitals(vitals)
                        showDialog = false
                    },
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

@Composable
fun AddVitalsDialog(
    onDismiss: () -> Unit,
    onSubmit: (Vitals) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var babyKicks by remember { mutableStateOf("") }

    AlertDialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Vitals", fontSize = 20.sp, fontWeight = FontWeight.Bold , color = Color(0xFF3F0A71)) },
        text = {
            Column(
                modifier = Modifier
                    .imePadding()
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = systolic,
                        onValueChange = { systolic = it },
                        label = { Text("Sys BP") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = diastolic,
                        onValueChange = { diastolic = it },
                        label = { Text("Dia BP") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                OutlinedTextField(
                    value = heartRate,
                    onValueChange = { heartRate = it },
                    label = { Text("Heart Rate") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = babyKicks,
                    onValueChange = { babyKicks = it },
                    label = { Text("Baby Kicks") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (systolic.isNotBlank() && diastolic.isNotBlank() && heartRate.isNotBlank() && weight.isNotBlank() && babyKicks.isNotBlank()) {
                        onSubmit(
                            Vitals(
                                systolic = systolic,
                                diastolic = diastolic,
                                heartRate = heartRate,
                                weight = weight,
                                babyKicks = babyKicks
                            )
                        )
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vitals added successfully!")
                        }
                        onDismiss()
                    }else{
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please enter all the vitals", duration = SnackbarDuration.Short)
                        }
                    }
                }
            ) {
                Text("Confirm" , color = Color(0xFF3F0A71) )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss" , color = Color(0xFF3F0A71))
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsLogAppBar(
    scrollBehaviour: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        scrollBehavior = scrollBehaviour,
        title = { Text(text = "Track My Pregnancy" , style = MaterialTheme.typography.headlineMedium , fontWeight = FontWeight.Bold ) },
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFC8ADFC),
            titleContentColor = Color(0xFF5F1C9C)
        ),
    )
}


/*
@Preview(showBackground = true)
@Composable
fun PreviewDialogBox() {
    AddVitalsDialog(
        onDismiss = { },
        onSubmit = { }
    )
}
*/


