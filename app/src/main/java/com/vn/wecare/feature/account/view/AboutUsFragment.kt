package com.vn.wecare.feature.account.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAboutUsBinding

class AboutUsFragment : BaseBindingFragment<FragmentAboutUsBinding>(
    FragmentAboutUsBinding::inflate
) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.aboutUsComposeView
        ) {
            AboutUsScreen(navigateBack = {
                findNavController().popBackStack()
            })
        }
    }
}