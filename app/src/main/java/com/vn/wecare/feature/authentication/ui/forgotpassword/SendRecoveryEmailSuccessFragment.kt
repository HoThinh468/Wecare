package com.vn.wecare.feature.authentication.ui.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSendRecoveryEmailSuccessBinding

class SendRecoveryEmailSuccessFragment :
    BaseBindingFragment<FragmentSendRecoveryEmailSuccessBinding>(
        FragmentSendRecoveryEmailSuccessBinding::inflate
    ) {
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.sendRecoveryEmailComposeView) {
            SendRecoveryEmailSuccessScreen {
                findNavController().navigate(R.id.action_sendRecoveryEmailSuccessFragment_to_logInFragment)
            }
        }
    }
}