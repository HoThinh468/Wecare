package com.vn.wecare.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentOnboardingBinding
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment :
    BaseBindingFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.onboardingComposeView) {
            OnboardingScreen(viewModel = onboardingViewModel, moveToSplashScreen = {
                findNavController().safeNavigate(
                    R.id.onboardingFragment,
                    R.id.action_onboardingFragment_to_splashFragment
                )
            })
        }
    }

    companion object {
        const val onboardingTag = "Onboarding flow"
    }
}