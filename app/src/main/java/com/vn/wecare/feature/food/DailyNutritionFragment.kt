package com.vn.wecare.feature.food

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyNutritionBinding

class DailyNutritionFragment :
    BaseBindingFragment<FragmentDailyNutritionBinding>(FragmentDailyNutritionBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.dailyNutritionComposeView
        ) {
            DailyNutritionScreen()
        }
    }
}