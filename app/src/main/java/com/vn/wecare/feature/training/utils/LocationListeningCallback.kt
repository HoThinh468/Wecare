package com.vn.wecare.feature.training.utils

import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineResult
import com.vn.wecare.feature.training.walking.WalkingFragment
import java.lang.ref.WeakReference

class LocationListeningCallback constructor(fragment: WalkingFragment) :
    LocationEngineCallback<LocationEngineResult> {

    private val activityWeakReference: WeakReference<WalkingFragment>

    init {this.activityWeakReference = WeakReference(fragment)}

    override fun onSuccess(result: LocationEngineResult) {
        result.lastLocation
    }
    override fun onFailure(exception: Exception) {

    }
}