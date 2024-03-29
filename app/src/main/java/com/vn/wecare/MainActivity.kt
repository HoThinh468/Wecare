package com.vn.wecare

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.applozic.mobicomkit.api.account.register.RegistrationResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vn.wecare.core.STEP_COUNT_SHARED_PREF
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.databinding.ActivityMainBinding
import com.vn.wecare.feature.exercises.workout_dashboard.ExerciseDashboardViewModel
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.usecase.CURRENT_STEP_FROM_SENSOR
import dagger.hilt.android.AndroidEntryPoint
import io.kommunicate.KmConversationBuilder
import io.kommunicate.KmConversationHelper
import io.kommunicate.Kommunicate
import io.kommunicate.callbacks.KMLoginHandler
import io.kommunicate.callbacks.KmCallback
import io.kommunicate.users.KMUser

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private var motionSensor: Sensor? = null

    private val stepCountViewModel: StepCountViewModel by viewModels()
    private val exerciseDashboardViewModel: ExerciseDashboardViewModel by viewModels()

    private lateinit var fab: View
    private var initialX: Float = 0F
    private var initialY: Float = 0F
    private var initialTouchX: Float = 0F
    private var initialTouchY: Float = 0F
    private var isMoved = false
    private var screenWidth: Int = 0

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
        Kommunicate.init(this, "2e65f69598334ef0acd5522dc37930b8e")

        // get screen width
        screenWidth = resources.displayMetrics.widthPixels

        fab = findViewById(R.id.fab)
        openChat()
        moveableFab()
    }

    private fun openChat() {
        fab.setOnClickListener {
            if (WecareUserSingletonObject.getInstance().userId != "") {
                openChatWithUser()
            } else {
                openChatWithGuest()
            }
        }
    }

    private fun moveableFab() {
        fab.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = view.x
                    initialY = view.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isMoved = false
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY
                    view.x = initialX + deltaX
                    view.y = initialY + deltaY
                    isMoved = true
                }

                MotionEvent.ACTION_UP -> {
                    // Calculate the new x-coordinate to move the FAB to the right or left edge
                    val halfFabWidth = view.width / 2
                    val newFabX: Float = if (view.x + halfFabWidth <= screenWidth / 2) {
                        // Move the FAB to the left edge
                        0F
                    } else {
                        // Move the FAB to the right edge
                        (screenWidth - view.width).toFloat()
                    }

                    // Animate the FAB to its new position
                    view.animate().x(newFabX).setDuration(200).start()

                    if (!isMoved) {
                        view.performClick()
                        openChat()
                    }
                }
            }
            true
        }
    }

    private fun openChatWithUser() {
        val user = KMUser()
        user.userId = WecareUserSingletonObject.getInstance().userId

        Kommunicate.login(this, user, object : KMLoginHandler {
            override fun onSuccess(registrationResponse: RegistrationResponse, context: Context) {
                KmConversationHelper.openConversation(
                    this@MainActivity,
                    false,
                    null,
                    object : KmCallback {
                        override fun onSuccess(message: Any) {}
                        override fun onFailure(error: Any) {}
                    })
            }

            override fun onFailure(
                registrationResponse: RegistrationResponse, exception: java.lang.Exception
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Open chat bot fail: " + exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun openChatWithGuest() {
        Kommunicate.loginAsVisitor(this, object : KMLoginHandler {
            override fun onSuccess(registrationResponse: RegistrationResponse, context: Context) {
                KmConversationBuilder(this@MainActivity).setSingleConversation(true)
                    .launchConversation(object : KmCallback {
                        override fun onSuccess(message: Any) {
                            Log.d("Conversation", "Success : $message")
                        }

                        override fun onFailure(error: Any) {
                            Log.d("Conversation", "Failure : $error")
                            Toast.makeText(
                                this@MainActivity, "Open chat bot fail: $error", Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

            override fun onFailure(
                registrationResponse: RegistrationResponse, exception: Exception
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Open chat bot fail: " + exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_UI)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 == null) return

        stepCountViewModel.updateCurrentSteps(p0.values[0])
        exerciseDashboardViewModel.updateCurrentSteps(p0.values[0])
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
                R.id.homeFragment, R.id.accountFragment, R.id.exerciseDashboardFragment, R.id.dailyNutritionFragment -> {
                    binding.navView.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }

                R.id.signUpFragment, R.id.logInFragment -> binding.fab.visibility = View.VISIBLE

                else -> {
                    binding.navView.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}