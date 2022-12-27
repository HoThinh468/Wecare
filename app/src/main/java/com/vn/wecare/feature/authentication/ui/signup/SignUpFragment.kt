package com.vn.wecare.feature.authentication.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentLogInBinding
import com.vn.wecare.databinding.FragmentSignUpBinding
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.feature.authentication.ui.login.SignInScreen
import com.vn.wecare.ui.theme.WecareTheme

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    SignUpScreen()
                }
            }
        }
        return view
    }
}