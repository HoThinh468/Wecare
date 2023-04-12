package com.vn.wecare.feature.authentication.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentLogInBinding
import com.vn.wecare.feature.home.step_count.di.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.usecase.LATEST_STEPS_COUNT
import com.vn.wecare.feature.home.step_count.usecase.PREVIOUS_TOTAL_SENSOR_STEPS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseBindingFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.composeView) {
            SignInScreen(
                navigateToHome = {
                    findNavController().navigate(R.id.action_global_authentication_nested_graph_to_home_fragment)
                },
                navigateToSignUp = {
                    findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
                },
                viewModel = loginViewModel,
                moveToForgotPasswordScreen = { findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment) },
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        val sharePref =
            requireActivity().getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        Log.d(
            "Login page latest step count: ", sharePref.getFloat(LATEST_STEPS_COUNT, 0f).toString()
        )
        Log.d(
            "Login page previous total sensor step count: ", sharePref.getFloat(
                PREVIOUS_TOTAL_SENSOR_STEPS, 0f
            ).toString()
        )
    }

    companion object {
        const val logInTag = "Login flow"
    }
}