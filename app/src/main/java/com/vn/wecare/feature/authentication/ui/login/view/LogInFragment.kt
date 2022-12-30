package com.vn.wecare.feature.authentication.ui.login.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentLogInBinding
import com.vn.wecare.feature.authentication.ui.login.LoginViewModel

class LogInFragment : BaseBindingFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.composeView) {
            SignInScreen(
                navigateToHome = {
                    findNavController().navigate(R.id.action_global_homeFragment)
                },
                navigateToSignUp = {
                    findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
                },
                viewModel = loginViewModel,
            )
        }
    }
}