package com.vn.wecare.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSplashBinding
import com.vn.wecare.utils.safeNavigate

class SplashFragment : BaseBindingFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val splashViewModel: SplashViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        if (splashViewModel.hasUser()) {
            splashViewModel.saveWecareUserToSingletonObject()
        } else {
            findNavController().safeNavigate(
                R.id.splashFragment, R.id.action_splashFragment_to_authentication_nested_graph
            )
        }
        super.setupComposeView(binding.splashComposeView) {
            SplashScreen(moveToOnboardingScreen = {
                findNavController().safeNavigate(
                    R.id.splashFragment, R.id.action_splashFragment_to_onboardingFragment
                )
            }, moveToHomeScreen = {
                findNavController().safeNavigate(
                    R.id.splashFragment, R.id.action_splashFragment_to_homeFragment
                )
            }, resetStartDestination = {
                resetHomeAsStartDestination()
            }, viewModel = splashViewModel
            )
        }
    }

    private fun resetHomeAsStartDestination() {
        val navGraph = findNavController().navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.homeFragment)
        findNavController().graph = navGraph
    }

    override fun onDestroyView() {
        super.onDestroyView()
        splashViewModel.resetResult()
    }

    companion object {
        const val splashFlowTag = "splash flow tag"
    }
}