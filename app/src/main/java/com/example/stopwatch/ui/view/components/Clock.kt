package com.example.stopwatch.ui.view.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatch.ui.theme.DarkPink
import com.example.stopwatch.ui.theme.LightGrey

@Composable
fun Clock(elapsedTime: String, progress: Float, clockSize: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        ),
        label = "ClockProgressAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        // Circular progress bar
        Canvas(modifier = Modifier.size(clockSize.dp)) {
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )

            drawArc(
                color = DarkPink,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = elapsedTime,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = LightGrey
        )
    }

}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
fun ClockPreview() {
    Clock("00:00:00", 0.2f, 200)
}