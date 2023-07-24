package com.vn.wecare.feature.training.onWalking

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.KeyEvent.KEYCODE_BACK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.core.lifecycle.requireMapboxNavigation
import com.vn.wecare.MainActivity
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentOnWalkingBinding
import com.vn.wecare.feature.training.utils.LocationListeningCallback
import com.vn.wecare.feature.training.utils.LocationPermissionHelper
import com.vn.wecare.feature.training.utils.UserAction
import com.vn.wecare.feature.training.widget.TargetIndex
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import kotlin.math.sin

@AndroidEntryPoint
class OnWalkingFragment : Fragment() {

    lateinit var mapView: MapView
    private var _binding: FragmentOnWalkingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OnWalkingViewModel by activityViewModels()

    private var oldLocation: CustomLocation = CustomLocation(0.0, 0.0)
    private lateinit var newLocation: CustomLocation
    private var distance : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var locationEngine: LocationEngine
    private val callback = LocationListeningCallback(this)
    private val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    private val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationEngine = LocationEngineProvider.getBestLocationEngine(context)
    }

    private val mapboxNavigation: MapboxNavigation by requireMapboxNavigation(
        onInitialize = this::initNavigation
    )

    private fun initNavigation() {
        MapboxNavigationApp.setup(
            NavigationOptions.Builder(MainActivity.appContext)
                .accessToken(getString(R.string.mapbox_access_token))
                .build()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnWalkingBinding.inflate(inflater, container, false)
        mapView = binding.mapView

        val btn = binding.btnNavigate
        btn.setOnClickListener {
            onCameraTrackingChanged()
        }

        getLocation()

        lateinit var result: Pair<UserTarget, TargetIndex>
        lateinit var userAction: UserAction
        setFragmentResultListener("userTarget") { requestKey, bundle ->
            result = bundle.get("userTarget") as Pair<UserTarget, TargetIndex>

            setFragmentResultListener("userAction2") { requestKey2, bundle2 ->
                userAction = bundle2.get("userAction2") as UserAction
                binding.composeView.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        WecareTheme {
                            OnWalkingScreen(
                                userTarget = result.first,
                                mapboxNavigation = mapboxNavigation,
                                onNavigateToSuccess = {
                                    Navigation.findNavController(requireView())
                                        .navigate(R.id.action_onWalkingFragment_to_doneFragment)
                                },
                                userAction = userAction,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionHelper = LocationPermissionHelper(WeakReference(activity))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        oldLocation = CustomLocation(0.0, 0.0)
        viewModel.reset()
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(16.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = ResourcesCompat.getDrawable(
                    requireActivity().resources,
                    R.drawable.user_puck_icon,
                    null
                ),
                shadowImage = ResourcesCompat.getDrawable(
                    requireActivity().resources,
                    R.drawable.user_icon_shadow,
                    null
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingChanged() {
        initLocationComponent()
        setupGesturesListener()
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(activity, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun getLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5f
        ) {
            if(oldLocation.latitude == 0.0) {
                oldLocation.latitude = it.latitude
                oldLocation.longitude = it.longitude
            } else {
                newLocation = CustomLocation(it.latitude, it.longitude)

                distance += distance(oldLocation.latitude, oldLocation.longitude, newLocation.latitude, newLocation.longitude)
                viewModel.updateNewDistance(distance)

                oldLocation.latitude = newLocation.latitude
                oldLocation.longitude = newLocation.longitude
            }
        }
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}

data class CustomLocation(
    var latitude: Double,
    var longitude: Double
)