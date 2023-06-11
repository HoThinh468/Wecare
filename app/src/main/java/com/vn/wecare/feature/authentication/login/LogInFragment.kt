package com.vn.wecare.feature.authentication.login

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.databinding.FragmentLogInBinding
import com.vn.wecare.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseBindingFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        Log.d(logInTag, "user singleton: ${WecareUserSingleton.getInstance()}")
        super.setupComposeView(binding.composeView) {
            SignInScreen(
                moveToSplash = {
                    findNavController().safeNavigate(
                        R.id.logInFragment,
                        R.id.action_global_authentication_nested_graph_to_splashFragment
                    )
                },
                navigateToSignUp = {
                    findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
                },
                viewModel = loginViewModel,
                moveToForgotPasswordScreen = {
                    findNavController().safeNavigate(
                        R.id.logInFragment, R.id.action_logInFragment_to_forgotPasswordFragment
                    )
                },
            )
        }
    }

    companion object {
        const val logInTag = "Login flow"
    }
}