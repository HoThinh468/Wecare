package com.vn.wecare.feature.home.step_count.ui.compose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.utils.common_composable.RequestPermission

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MotionSensorTrack(
    stepCountViewModel: StepCountViewModel,
) {
    val context = LocalContext.current

    val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val motionSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    if (motionSensor != null) {
        val motionSensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // No action needed at this function
            }

            override fun onSensorChanged(event: SensorEvent) {
                stepCountViewModel.calculateCurrentSteps(event.values[0])
            }
        }

        sensorManager.registerListener(
            motionSensorEventListener,
            motionSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }
}