package com.vn.wecare.feature.training.utils

import androidx.fragment.app.Fragment
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineResult
import com.vn.wecare.feature.training.walking.WalkingFragment
import java.lang.ref.WeakReference

class LocationListeningCallback constructor(fragment: Fragment) :
    LocationEngineCallback<LocationEngineResult> {

    private val activityWeakReference: WeakReference<Fragment>

    init {this.activityWeakReference = WeakReference(fragment)}

    override fun onSuccess(result: LocationEngineResult) {
        result.lastLocation
    }
    override fun onFailure(exception: Exception) {

    }
}