package com.vn.wecare.feature.food.snack

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSnackBinding

class SnackFragment : BaseBindingFragment<FragmentSnackBinding>(
    FragmentSnackBinding::inflate
) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.snackComposeView
        ) {

        }
    }
}