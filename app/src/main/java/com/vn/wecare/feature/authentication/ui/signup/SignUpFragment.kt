package com.vn.wecare.feature.authentication.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSignUpBinding

class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.composeView) {
            SignUpScreen(
                viewModel = signUpViewModel,
                moveToHomeScreen = {
                    findNavController().navigate(R.id.action_global_homeFragment)
                },
                navigateBack = { findNavController().popBackStack() },
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        auth = Firebase.auth
    }
}