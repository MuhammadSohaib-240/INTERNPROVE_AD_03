package com.example.stopwatch.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stopwatch.ui.theme.StopwatchTheme
import com.example.stopwatch.viewmodel.StopwatchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stopwatch.model.Lap
import com.example.stopwatch.ui.theme.LightGrey
import com.example.stopwatch.ui.theme.OffWhite
import com.example.stopwatch.ui.view.components.Clock

@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel = viewModel()) {
    val elapsedTime by viewModel.elapsedTimeLiveData.observeAsState("00:00:00")
    val laps by viewModel.lapsLiveData.observeAsState(emptyList())
    val progress by viewModel.progressLiveData.observeAsState(0f)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Stopwatch Section with Animated Circle
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .padding(horizontal = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Clock(elapsedTime, progress = progress, 600)
                }

                // Lap Table Section
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(laps.size) { index ->
                        Lap(index + 1, laps[index])
                    }
                }

                // Buttons Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 15.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { viewModel.start() }) {
                        Text("Start")
                    }

                    Button(onClick = { viewModel.stop() }) {
                        Text("Stop")
                    }

                    Button(onClick = { viewModel.reset() }) {
                        Text("Reset")
                    }

                    Button(onClick = { viewModel.recordLap() }) {
                        Text("Lap")
                    }
                }
            }
        }
    )
}

@Composable
fun Lap(lapNumber: Int, lap: Lap) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(OffWhite),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Lap $lapNumber",
            color = LightGrey,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(
            text = lap.totalElapsedTime,
            color = LightGrey
        )
        Text(
            text = lap.lapDifference,
            color = LightGrey,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 667)
@Composable
fun StopwatchScreenPreview() {
    StopwatchTheme {
        StopwatchScreen()
    }
}
