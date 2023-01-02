package com.vn.wecare.feature.training.done_training

import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.core.replay.MapboxReplayer
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.core.trip.session.RouteProgressObserver
import com.mapbox.navigation.ui.maps.camera.NavigationCamera
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import com.mapbox.navigation.ui.maps.camera.transition.NavigationCameraTransitionOptions
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import com.mapbox.navigation.ui.maps.route.arrow.api.MapboxRouteArrowApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLine
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineColorResources
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineResources
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentDoneBinding
import com.vn.wecare.databinding.FragmentOnWalkingBinding
import com.vn.wecare.feature.training.onWalking.ExitOrView
import com.vn.wecare.feature.training.onWalking.StopAndPause
import com.vn.wecare.feature.training.onWalking.UserTarget
import com.vn.wecare.feature.training.widget.TargetChosen
import com.vn.wecare.ui.theme.WecareTheme
import kotlinx.coroutines.Job

class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ExitOrView(
                        onExit = {
                            Navigation.findNavController(requireView()).popBackStack()
                            Navigation.findNavController(requireView()).popBackStack()
                            Navigation.findNavController(requireView()).popBackStack()
                        },
                        onView = {
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_doneFragment_to_viewRouteFragment)
                        })
                }
            }
        }
        return binding.root
    }
}