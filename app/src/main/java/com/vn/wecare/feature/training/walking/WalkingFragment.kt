package com.vn.wecare.feature.training.walking

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentWalkingBinding
import com.vn.wecare.feature.training.dashboard.TopBar
import com.vn.wecare.feature.training.onWalking.UserTarget
import com.vn.wecare.feature.training.widget.TargetChosen
import com.vn.wecare.feature.training.utils.LocationListeningCallback
import com.vn.wecare.feature.training.utils.LocationPermissionHelper
import com.vn.wecare.feature.training.utils.UserAction
import com.vn.wecare.feature.training.utils.convertUserActionToString
import com.vn.wecare.feature.training.widget.TargetIndex
import com.vn.wecare.ui.theme.WecareTheme
import java.lang.ref.WeakReference

class WalkingFragment : Fragment() {

    lateinit var mapView: MapView
    private var _binding: FragmentWalkingBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalkingBinding.inflate(inflater, container, false)
        mapView = binding.mapView

        val btn = binding.btnNavigate
        btn.setOnClickListener {
            onCameraTrackingChanged()
        }

        lateinit var result: UserAction
        binding.targetChosen.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    TargetChosen(
                        goScreen = { userTarget, target ->
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_walkingFragment_to_onWalkingFragment)

                            setFragmentResult("userTarget", bundleOf("userTarget" to Pair(userTarget, target)))
                        },

                    )
                }
            }
        }
        setFragmentResultListener("userAction") { requestKey, bundle ->
            result = bundle.get("userAction") as UserAction
            binding.appBar.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    WecareTheme {
                        TopBar(text = convertUserActionToString(result), navigateBack = {
                            Navigation.findNavController(requireView()).popBackStack()
                        })
                    }
                }
                setFragmentResult("userAction2", bundleOf("userAction2" to result))
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

    private fun onCameraTrackingChanged() {
        initLocationComponent()
        setupGesturesListener()
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
}


