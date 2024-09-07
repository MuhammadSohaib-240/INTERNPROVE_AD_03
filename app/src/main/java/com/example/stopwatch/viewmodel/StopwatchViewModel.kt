package com.example.stopwatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stopwatch.model.Lap
import com.example.stopwatch.model.StopwatchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {
    private val stopwatchModel = StopwatchModel()

    private val _elapsedTimeLiveData = MutableLiveData<String>()
    val elapsedTimeLiveData: LiveData<String> = _elapsedTimeLiveData

    private val _progressLiveData = MutableLiveData<Float>()
    val progressLiveData: LiveData<Float> = _progressLiveData

    private val _lapsLiveData = MutableLiveData<List<Lap>>()
    val lapsLiveData: LiveData<List<Lap>> = _lapsLiveData

    private val _isRunningLiveData = MutableLiveData<Boolean>()
    val isRunningLiveData: LiveData<Boolean> = _isRunningLiveData

    private val totalDuration = 60000L

    fun start() {
        if (!stopwatchModel.isRunning()) {
            stopwatchModel.start()
            _isRunningLiveData.value = stopwatchModel.isRunning()

            viewModelScope.launch(Dispatchers.IO) {
                while (stopwatchModel.isRunning()) {
                    val elapsedTime = stopwatchModel.getElapsedTime()
                    _elapsedTimeLiveData.postValue(elapsedTime)

                    val totalElapsedTime = stopwatchModel.getTotalElapsedTime()
                    val progress = (totalElapsedTime % totalDuration).toFloat() / totalDuration
                    _progressLiveData.postValue(progress)

                    delay(10)
                }
                _isRunningLiveData.postValue(false)
            }
        }
    }

    fun stop() {
        if (stopwatchModel.isRunning()) {
            stopwatchModel.stop()
            _isRunningLiveData.value = stopwatchModel.isRunning()
            _elapsedTimeLiveData.postValue(stopwatchModel.getElapsedTime())
        }
    }

    fun reset() {
        stopwatchModel.reset()
        _isRunningLiveData.value = false
        _elapsedTimeLiveData.value = stopwatchModel.getElapsedTime()
        _progressLiveData.value = 0f
        updateLaps()
    }

    fun recordLap() {
        stopwatchModel.recordLap()
        updateLaps()
    }

    private fun updateLaps() {
        _lapsLiveData.value = stopwatchModel.getLaps().toList()
    }
}
