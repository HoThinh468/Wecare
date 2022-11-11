package com.vn.wecare.feature.home.step_count

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.feature.home.step_count.ui.StepCountViewModel
import com.vn.wecare.utils.common_composable.RequestPermission

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MotionSensorTrack(
    stepCountViewModel: StepCountViewModel
) {

    RequestPermission(
        permission = android.Manifest.permission.ACTIVITY_RECOGNITION
    )
    
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
                stepCountViewModel.calculateCurrentCurrentSteps(event.values[0])
                stepCountViewModel.calculateCaloConsumed()
            }
        }

        sensorManager.registerListener(
            motionSensorEventListener,
            motionSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }
}