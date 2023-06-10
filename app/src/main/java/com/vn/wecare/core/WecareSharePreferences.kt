package com.vn.wecare.core

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val STEP_COUNT_SHARED_PREF = "step_count_shared_pref"

@Singleton
class WecareSharePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getDefaultSharedPref(key: String): SharedPreferences =
        context.getSharedPreferences(key, Context.MODE_PRIVATE)

    fun getStepCountSharedPref(): SharedPreferences =
        context.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
}