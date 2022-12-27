package com.vn.wecare.feature.training.ui.view_route

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.mapbox.android.gestures.Utils
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.ExperimentalPreviewMapboxNavigationAPI
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.core.replay.MapboxReplayer
import com.mapbox.navigation.core.replay.history.ReplayEventBase
import com.mapbox.navigation.core.replay.history.ReplaySetNavigationRoute
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.core.trip.session.RouteProgressObserver
import com.mapbox.navigation.ui.maps.NavigationStyles
import com.mapbox.navigation.ui.maps.camera.NavigationCamera
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import com.mapbox.navigation.ui.maps.camera.transition.NavigationCameraTransitionOptions
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import com.mapbox.navigation.ui.maps.route.RouteLayerConstants
import com.mapbox.navigation.ui.maps.route.arrow.api.MapboxRouteArrowApi
import com.mapbox.navigation.ui.maps.route.arrow.api.MapboxRouteArrowView
import com.mapbox.navigation.ui.maps.route.arrow.model.RouteArrowOptions
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLine
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineColorResources
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineResources
import com.vn.wecare.MainActivity
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentDoneBinding
import com.vn.wecare.databinding.FragmentViewRouteBinding
import com.vn.wecare.feature.training.ui.view_route.widget.HistoryFileLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

private const val DEFAULT_INITIAL_ZOOM = 15.0

class ViewRouteFragment : Fragment() {

    private var _binding: FragmentViewRouteBinding? = null
    private val binding get() = _binding!!

    private var loadNavigationJob: Job? = null
    private val navigationLocationProvider = NavigationLocationProvider()
    private lateinit var historyFileLoader: HistoryFileLoader
    private lateinit var mapboxNavigation: MapboxNavigation
    private lateinit var mapboxReplayer: MapboxReplayer
    private lateinit var locationComponent: LocationComponentPlugin
    private lateinit var navigationCamera: NavigationCamera
    private lateinit var viewportDataSource: MapboxNavigationViewportDataSource
    private var isLocationInitialized = false
    private val pixelDensity = Resources.getSystem().displayMetrics.density
    private val overviewPadding: EdgeInsets by lazy {
        EdgeInsets(
            140.0 * pixelDensity,
            40.0 * pixelDensity,
            120.0 * pixelDensity,
            40.0 * pixelDensity
        )
    }
    private val landscapeOverviewPadding: EdgeInsets by lazy {
        EdgeInsets(
            30.0 * pixelDensity,
            380.0 * pixelDensity,
            20.0 * pixelDensity,
            20.0 * pixelDensity
        )
    }
    private val followingPadding: EdgeInsets by lazy {
        EdgeInsets(
            180.0 * pixelDensity,
            40.0 * pixelDensity,
            150.0 * pixelDensity,
            40.0 * pixelDensity
        )
    }
    private val landscapeFollowingPadding: EdgeInsets by lazy {
        EdgeInsets(
            30.0 * pixelDensity,
            380.0 * pixelDensity,
            110.0 * pixelDensity,
            40.0 * pixelDensity
        )
    }

    private val initialCameraOptions: CameraOptions? = CameraOptions.Builder()
        .zoom(DEFAULT_INITIAL_ZOOM)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewRouteBinding.inflate(inflater, container, false)

        initNavigation()
        handleHistoryFileSelected()
        initMapStyle()

        setupReplayControls()
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun handleHistoryFileSelected() {
        loadNavigationJob = CoroutineScope(Dispatchers.Main).launch {
            val events = historyFileLoader
                .loadReplayHistory(requireContext())
            mapboxReplayer.clearEvents()
            mapboxReplayer.pushEvents(events)
            binding.playReplay.visibility = View.VISIBLE
            mapboxNavigation.resetTripSession()
            mapboxNavigation.setRoutes(emptyList())
            isLocationInitialized = false
            mapboxReplayer.playFirstLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMapStyle() {
        viewportDataSource = MapboxNavigationViewportDataSource(
            binding.mapView.getMapboxMap()
        )
        val mapboxMap = binding.mapView.getMapboxMap()
        navigationCamera = NavigationCamera(
            mapboxMap,
            binding.mapView.camera,
            viewportDataSource
        )
        initialCameraOptions?.let { mapboxMap.setCamera(it) }
        mapboxMap.loadStyleUri(
            NavigationStyles.NAVIGATION_DAY_STYLE,
            {
                locationComponent = binding.mapView.location.apply {
                    this.locationPuck = LocationPuck2D(
                        bearingImage = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.mapbox_navigation_puck_icon
                        )
                    )
                    setLocationProvider(navigationLocationProvider)
                    enabled = true
                }
                locationComponent.addOnIndicatorPositionChangedListener(onPositionChangedListener)
                viewportDataSource.evaluate()
            },
            object : OnMapLoadErrorListener {
                override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
                    // intentionally blank
                }
            }
        )
    }

    @SuppressLint("MissingPermission")
    private fun initNavigation() {
        historyFileLoader = HistoryFileLoader()
        mapboxNavigation = MapboxNavigationProvider.create(
            NavigationOptions.Builder(MainActivity.appContext)
                .accessToken(getString(R.string.mapbox_access_token))
                .build()
        )
        startReplayTripSession()
    }

    @OptIn(ExperimentalPreviewMapboxNavigationAPI::class)
    private fun startReplayTripSession() {
        mapboxReplayer = mapboxNavigation.mapboxReplayer
        mapboxNavigation.startReplayTripSession()
    }

    override fun onStart() {
        super.onStart()

        if (::mapboxNavigation.isInitialized) {
            mapboxNavigation.registerLocationObserver(locationObserver)
            mapboxNavigation.registerRoutesObserver(routesObserver)
            mapboxNavigation.registerRouteProgressObserver(routeProgressObserver)
        }
    }

    override fun onStop() {
        super.onStop()
        if (::mapboxNavigation.isInitialized) {
            mapboxNavigation.unregisterLocationObserver(locationObserver)
            mapboxNavigation.unregisterRoutesObserver(routesObserver)
            mapboxNavigation.unregisterRouteProgressObserver(routeProgressObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        routeLineApi.cancel()
        routeLineView.cancel()
        mapboxReplayer.finish()
        mapboxNavigation.onDestroy()
        if (::locationComponent.isInitialized) {
            locationComponent.removeOnIndicatorPositionChangedListener(onPositionChangedListener)
        }
    }

    private val onPositionChangedListener = OnIndicatorPositionChangedListener { point ->
        val result = routeLineApi.updateTraveledRouteLine(point)
        binding.mapView.getMapboxMap().getStyle()?.apply {
            // Render the result to update the map.
            routeLineView.renderRouteLineUpdate(this, result)
        }
    }

    private val locationObserver = object : LocationObserver {
        override fun onNewRawLocation(rawLocation: Location) {}
        override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {
            viewportDataSource.onLocationChanged(locationMatcherResult.enhancedLocation)
            viewportDataSource.evaluate()
            if (!isLocationInitialized) {
                isLocationInitialized = true
                val instantTransition = NavigationCameraTransitionOptions.Builder()
                    .maxDuration(0)
                    .build()
                navigationCamera.requestNavigationCameraToOverview(
                    stateTransitionOptions = instantTransition,
                )
            }

            navigationLocationProvider.changePosition(
                locationMatcherResult.enhancedLocation,
                locationMatcherResult.keyPoints,
            )
        }
    }

    private val routesObserver: RoutesObserver = RoutesObserver { result ->
        if (result.routes.isEmpty()) {
            viewportDataSource.clearRouteData()
        } else {
            viewportDataSource.onRouteChanged(result.routes.first())
        }
        viewportDataSource.evaluate()

        val routeLines = result.routes.map { RouteLine(it, null) }
        routeLineApi.setRoutes(
            routeLines
        ) { value ->
            binding.mapView.getMapboxMap().getStyle()?.apply {
                routeLineView.renderRouteDrawData(this, value)
            }
        }
    }

    private val routeProgressObserver = RouteProgressObserver { routeProgress ->
        viewportDataSource.onRouteProgressChanged(routeProgress)
        viewportDataSource.evaluate()

        routeLineApi.updateWithRouteProgress(routeProgress) { result ->
            binding.mapView.getMapboxMap().getStyle()?.apply {
                routeLineView.renderRouteLineUpdate(this, result)
            }
        }
        val arrowUpdate = routeArrowApi.addUpcomingManeuverArrow(routeProgress)
        binding.mapView.getMapboxMap().getStyle()?.apply {
            routeArrowView.renderManeuverUpdate(this, arrowUpdate)
        }
    }

    private val routeLineView by lazy {
        MapboxRouteLineView(options)
    }

    private val routeLineApi: MapboxRouteLineApi by lazy {
        MapboxRouteLineApi(options)
    }

    private val routeArrowApi: MapboxRouteArrowApi by lazy {
        MapboxRouteArrowApi()
    }

    private val options: MapboxRouteLineOptions by lazy {
        MapboxRouteLineOptions.Builder(requireContext())
            .withRouteLineResources(
                RouteLineResources.Builder()
                    .routeLineColorResources(
                        RouteLineColorResources.Builder().build()
                    )
                    .build()
            )
            .withRouteLineBelowLayerId("road-label-navigation")
            .withVanishingRouteLineEnabled(true)
            .build()
    }

    private val routeArrowView: MapboxRouteArrowView by lazy {
        MapboxRouteArrowView(
            RouteArrowOptions.Builder(requireContext())
                .withAboveLayerId(RouteLayerConstants.TOP_LEVEL_ROUTE_LINE_LAYER_ID)
                .build()
        )
    }

    @SuppressLint("MissingPermission")
    private fun setupReplayControls() {
        binding.seekBar.max = 8
        binding.seekBar.progress = 1
        binding.seekBarText.text = getString(
            R.string.replay_playback_speed_seekbar,
            binding.seekBar.progress
        )
        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    mapboxReplayer.playbackSpeed(progress.toDouble())
                    binding.seekBarText.text = getString(
                        R.string.replay_playback_speed_seekbar,
                        progress
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            }
        )

        binding.playReplay.setOnClickListener {
            mapboxReplayer.play()
            binding.playReplay.visibility = View.GONE
            navigationCamera.requestNavigationCameraToFollowing()
        }

        mapboxReplayer.registerObserver { events ->
            updateReplayStatus(events)
            events.forEach {
                when (it) {
                    is ReplaySetNavigationRoute -> setRoute(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateReplayStatus(playbackEvents: List<ReplayEventBase>) {
        playbackEvents.lastOrNull()?.eventTimestamp?.let {
            val currentSecond = mapboxReplayer.eventSeconds(it).toInt()
            val durationSecond = mapboxReplayer.durationSeconds().toInt()
            binding.playerStatus.text = "$currentSecond:$durationSecond"
        }
    }

    private fun setRoute(replaySetRoute: ReplaySetNavigationRoute) {
        replaySetRoute.route?.let { directionRoute ->
            mapboxNavigation.setNavigationRoutes(Collections.singletonList(directionRoute))
        }
    }
}