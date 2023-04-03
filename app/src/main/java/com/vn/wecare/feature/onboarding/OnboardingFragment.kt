package com.vn.wecare.feature.onboarding

import androidx.compose.ui.platform.ComposeView
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentOnboardingBinding

class OnboardingFragment : BaseBindingFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: (() -> Unit)?) {
        super.setupComposeView(binding.onboardingComposeView) {
            OnboardingScreen()
        }
    }
}