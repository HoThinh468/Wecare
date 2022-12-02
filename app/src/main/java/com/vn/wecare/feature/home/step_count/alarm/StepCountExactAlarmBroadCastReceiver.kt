package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class StepCountExactAlarmBroadCastReceiver : BroadcastReceiver() {
    private fun showToast(context: Context) {
        Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
    }

    override fun onReceive(context: Context, intent: Intent?) {
        showToast(context)
    }
}