package com.vn.wecare

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vn.wecare.core.STEP_COUNT_SHARED_PREF
import com.vn.wecare.databinding.ActivityMainBinding
import com.vn.wecare.feature.home.HomeViewModel
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.usecase.CURRENT_STEP_FROM_SENSOR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private var motionSensor: Sensor? = null

    private val stepCountViewModel: StepCountViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup sensor manger and check if motion sensor is available
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        appContext = applicationContext

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(setUpNavController())

        hideBottomNavBar(setUpNavController())
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_UI)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 == null) return
        stepCountViewModel.updateCurrentSteps(p0.values[0])
        homeViewModel.updateCurrentSteps(p0.values[0])
        val sharePref = getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        with(sharePref.edit()) {
            putFloat(CURRENT_STEP_FROM_SENSOR, p0.values[0])
            apply()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { /* Do nothing */
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        motionSensor = null
    }

    private fun setUpNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun hideBottomNavBar(navController: NavController) {
        navController.addOnDestinationChangedListener { _: NavController?, navDestination: NavDestination, _: Bundle? ->
            when (navDestination.id) {
                R.id.homeFragment, R.id.accountFragment, R.id.exercisesFragment, R.id.dailyNutritionFragment -> binding.navView.visibility =
                    View.VISIBLE

                else -> binding.navView.visibility = View.GONE
            }
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}