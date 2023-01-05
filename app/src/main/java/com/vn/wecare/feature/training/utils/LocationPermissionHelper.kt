package com.vn.wecare.feature.training.utils

import android.app.Activity
import android.widget.Toast
import com.mapbox.common.location.compat.permissions.PermissionsListener
import com.mapbox.common.location.compat.permissions.PermissionsManager
import java.lang.ref.WeakReference

class LocationPermissionHelper(val fragment: WeakReference<Activity>) {
  private lateinit var permissionsManager: PermissionsManager

  fun checkPermissions(onMapReady: () -> Unit) {
    if (PermissionsManager.areLocationPermissionsGranted(fragment.get())) {
      onMapReady()
    } else {
      permissionsManager = PermissionsManager(object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
          Toast.makeText(
            fragment.get(), "You need to accept location permissions.",
            Toast.LENGTH_SHORT
          ).show()
        }

        override fun onPermissionResult(granted: Boolean) {
          if (granted) {
            onMapReady()
          } else {
            fragment.get()?.finish()
          }
        }
      })
      permissionsManager.requestLocationPermissions(fragment.get())
    }
  }

  fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}