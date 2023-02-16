package com.vn.wecare

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vn.wecare.databinding.ActivityMainBinding
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.di.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.usecase.LATEST_STEPS_COUNT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private var motionSensor: Sensor? = null

    private val stepCountViewModel: StepCountViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup sensor manger and check if motion sensor is available
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        appContext = applicationContext

        auth = Firebase.auth

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (auth.currentUser == null) {
            navGraph.setStartDestination(R.id.authentication_nested_graph)
            navController.graph = navGraph
        }
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_: NavController?, navDestination: NavDestination, _: Bundle? ->
            when(navDestination.id) {
                R.id.homeFragment, R.id.accountFragment2, R.id.exercisesFragment, R.id.newFeedFragment -> binding.navView.visibility = View.VISIBLE
                else -> binding.navView.visibility = View.GONE
            }
        }
    }

    companion object {
        lateinit var appContext: Context
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_UI)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 == null) return
        stepCountViewModel.updateCurrentSteps(p0.values[0])
        val sharePref = getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        with(sharePref.edit()) {
            Log.d("Sensor changes: ", p0.values[0].toString())
            putFloat(LATEST_STEPS_COUNT, p0.values[0])
            apply()
        }
//        stepCountViewModel.updateCaloriesConsumed()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Nothing to do
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        motionSensor = null
    }
}