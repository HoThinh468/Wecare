package com.vn.wecare.feature.authentication.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSignUpBinding

class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.composeView) {
            SignUpScreen(
                viewModel = signUpViewModel,
                moveToOnboardingScreen = {
                    findNavController().navigate(R.id.action_global_onboardingFragment)
                },
                navigateBack = { findNavController().popBackStack() },
            )
        }
    }
}