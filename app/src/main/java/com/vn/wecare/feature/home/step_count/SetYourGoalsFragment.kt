package com.vn.wecare.feature.home.step_count

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vn.wecare.databinding.FragmentSetYourGoalsBinding
import com.vn.wecare.ui.theme.WecareTheme

@OptIn(ExperimentalMaterialApi::class)
class SetYourGoalsFragment: Fragment() {

    private var _binding: FragmentSetYourGoalsBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetYourGoalsBinding.inflate(inflater, container, false)

        binding.setYourGoalsComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    SetYourGoalScreen(
                        navigateUp = { findNavController().popBackStack() },
                    )
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}