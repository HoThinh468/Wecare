package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.vn.wecare.feature.home.step_count.usecase.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepCountExactAlarmBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase

    @Inject
    lateinit var updatePreviousTotalSensorSteps: UpdatePreviousTotalSensorSteps

    @Inject
    lateinit var getStepsPerDayUsecase: GetStepsPerDayUsecase

    @Inject
    lateinit var saveStepsPerDayUsecase: SaveStepsPerDayUsecase

    @Inject
    lateinit var saveStepsPerDayToFirestoreUsecase: SaveStepsPerDayToFirestoreUsecase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        GlobalScope.launch {
            // Update stepsPerDay
            getStepsPerDayUsecase.getCurrentDaySteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
                .collect {
                    saveStepsPerDayUsecase.saveStepsPerDayToDb(it)
                    if (checkInternetConnection(context)) {
                        saveStepsPerDayToFirestoreUsecase.saveStepsPerDayToFirestore(it)
                    }
                }
        }

        // Update previous total steps
        updatePreviousTotalSensorSteps.updatePreviousTotalSensorStepCount(
            getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor()
        )
    }

    private fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            // else return false
            else -> false
        }
    }
}