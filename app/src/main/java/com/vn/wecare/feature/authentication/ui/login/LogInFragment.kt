package com.vn.wecare.feature.authentication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentLogInBinding
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.feature.authentication.ui.signup.SignUpScreen
import com.vn.wecare.feature.training.ui.dashboard.TrainingScreen
import com.vn.wecare.ui.theme.WecareTheme

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    SignInScreen (
                        navigateToHome = { Navigation.findNavController(requireView())
                            .navigate(R.id.action_logInFragment_to_homeFragment) },
                        navigateToSignUp = { Navigation.findNavController(requireView())
                            .navigate(R.id.action_logInFragment_to_signUpFragment) },
                    )
                }
            }
        }
        return view
    }
}