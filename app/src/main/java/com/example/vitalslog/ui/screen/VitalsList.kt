package com.example.vitalslog.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitalslog.R
import com.example.vitalslog.data.model.Vitals
import com.example.vitalslog.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VitalsCard(
    modifier: Modifier = Modifier,
    vitals: Vitals
) {
    val date = formatTimeMillis(vitals.timestamp)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEBB9FE))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    HealthStat(drawableId = R.drawable.heart, value = "${vitals.heartRate} bpm")
                    Spacer(modifier = Modifier.height(12.dp))
                    HealthStat(drawableId = R.drawable.weight, value = "${vitals.weight} kg")
                }
                Column(modifier = Modifier.weight(1f)) {
                    HealthStat(drawableId = R.drawable.heart, value = "${vitals.systolic}/${vitals.diastolic} mmHg")
                    Spacer(modifier = Modifier.height(12.dp))
                    HealthStat(drawableId = R.drawable.baby, value = "${vitals.babyKicks} kicks")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF9C4DB9))
                    .padding(12.dp)
            ) {
                Text(
                    text = date,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Composable
fun HealthStat(drawableId: Int, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun VitalsList(
    vitalsList: List<Vitals>,
    homeViewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ){
        items(vitalsList){ vital ->
            VitalsCard(
                vitals = vital
            )
        }
    }
}

fun formatTimeMillis(timeMillis: Long): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.ENGLISH)
    return dateFormat.format(Date(timeMillis))
}

@Preview(showBackground = true)
@Composable
fun PreviewVitalsCard() {
    VitalsCard(
        vitals = Vitals(
            id = 1,
            systolic = "120",
            diastolic = "80",
            heartRate = "72",
            weight = "65.5f",
            babyKicks = "10",
            timestamp = System.currentTimeMillis()
        )
    )
}


