package com.vn.wecare.feature.authentication.ui.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment :
    BaseBindingFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.forgotPasswordComposeView) {
            ForgotPasswordScreen(
                navigateUp = { findNavController().popBackStack() },
                viewModel = viewModel,
                moveToSendSuccessEmailScreen = {
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_sendRecoveryEmailSuccessFragment)
                }
            )
        }
    }
}