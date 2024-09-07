package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.stopwatch.ui.theme.StopwatchTheme
import com.example.stopwatch.ui.view.screens.StopwatchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopwatchTheme {
                StopwatchScreen()
            }
        }
    }
}
