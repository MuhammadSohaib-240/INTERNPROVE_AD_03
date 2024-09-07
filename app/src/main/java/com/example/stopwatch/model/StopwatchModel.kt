package com.example.stopwatch.model

import java.util.Locale
import kotlin.math.abs

class StopwatchModel(
    private var startTime: Long = 0,
    private var elapsedTime: Long = 0,
    private var isRunning: Boolean = false,
    private val laps: MutableList<Lap> = mutableListOf()
) {
    private var lastLapTime: Long = 0

    fun start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis()
            isRunning = true
        }
    }

    fun stop() {
        if (isRunning) {
            elapsedTime += System.currentTimeMillis() - startTime
            isRunning = false
        }
    }

    fun reset() {
        startTime = 0
        elapsedTime = 0
        isRunning = false
        laps.clear()
        lastLapTime = 0
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    fun getElapsedTime(): String {
        val time = if (isRunning) System.currentTimeMillis() - startTime + elapsedTime else elapsedTime
        return formatTime(time)
    }

    fun recordLap() {
        val totalElapsed = System.currentTimeMillis() - startTime + elapsedTime
        val lapTime = totalElapsed - lastLapTime

        val lapDifference = if (laps.isNotEmpty()) {
            lapTime
        } else {
            lapTime
        }

        laps.add(Lap(
            lapTime = formatTime(lapTime),
            totalElapsedTime = formatTime(totalElapsed),
            lapDifference = formatTime(lapDifference)
        ))

        lastLapTime = totalElapsed
    }

    fun getLaps(): List<Lap> {
        return laps
    }

    fun getTotalElapsedTime(): Long {
        return if (isRunning) System.currentTimeMillis() - startTime + elapsedTime else elapsedTime
    }

    private fun formatTime(timeMillis: Long): String {
        val absoluteMillis = abs(timeMillis)
        val milliseconds = (absoluteMillis % 1000) / 10
        val seconds = (absoluteMillis / 1000) % 60
        val minutes = (absoluteMillis / (1000 * 60)) % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, milliseconds)
    }
}
