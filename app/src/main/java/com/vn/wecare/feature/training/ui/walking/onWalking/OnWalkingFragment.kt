package com.vn.wecare.feature.training.ui.walking.onWalking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.mapbox.navigation.base.options.HistoryRecorderOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.core.lifecycle.requireMapboxNavigation
import com.vn.wecare.MainActivity
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentOnWalkingBinding
import com.vn.wecare.ui.theme.WecareTheme

class OnWalkingFragment : Fragment() {

    private var _binding: FragmentOnWalkingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        lateinit var result: UserTarget
        setFragmentResultListener("userTarget") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            result = bundle.get("userTarget") as UserTarget
            // Do something with the result
            binding.composeView.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    WecareTheme {
                        OnWalkingScreen(
                            userTarget = result,
                            mapboxNavigation = mapboxNavigation,
                            onNavigateToSuccess = {
                                Navigation.findNavController(requireView())
                                    .navigate(R.id.action_onWalkingFragment_to_doneFragment)
                            }
                        )
                    }
                }
            }
        }
        return binding.root
    }
}