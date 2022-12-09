package com.vn.wecare.feature.home.step_count.ui.compose

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject

//class MotionSensor @Inject constructor(
//    @ApplicationContext private val context: Context
//) {
//
//    private val sensorManager: SensorManager =
//        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//
//    private val motionSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//    fun isMotionSensorExist(): Boolean {
//        return (motionSensor != null)
//    }
//
//    var currentSteps = mutableStateOf(0f)
//
//    fun getCurrentStepsFromSensor(): Float {
//        val motionSensorEventListener = object : SensorEventListener {
//            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//                // No action needed at this function
//            }
//
//            override fun onSensorChanged(event: SensorEvent) {
//                currentSteps.value = event.values[0]
//                Log.d("Steps: ", event.values[0].toString())
//            }
//        }
//
//        sensorManager.registerListener(
//            motionSensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_UI
//        )
//
//        return currentSteps.value
//    }
//}

class MotionSensor @Inject constructor() : SensorEventListener {

    var currentSteps = mutableStateOf(0f)
        private set

    override fun onSensorChanged(event: SensorEvent) {
        currentSteps.value = event.values[0]
        Log.d("ABcdXyz: ", event.values[0].toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Nothing to do with this function
    }
}