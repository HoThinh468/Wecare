package com.vn.wecare.feature.food.mealdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentMealDetailBinding
import com.vn.wecare.feature.food.breakfast.ui.MEAL_KEY
import com.vn.wecare.feature.food.breakfast.ui.MEAL_RECORD
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey

class MealDetailFragment : BaseBindingFragment<FragmentMealDetailBinding>(
    FragmentMealDetailBinding::inflate
) {

    private val viewModel: MealDetailViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val record: MealRecordModel = arguments?.getParcelable(MEAL_RECORD) ?: MealRecordModel()
        val mealTypeKey: MealTypeKey =
            (arguments?.getSerializable(MEAL_KEY) ?: MealTypeKey.BREAKFAST) as MealTypeKey
        super.setupComposeView(binding.mealDetailComposeView) {
            MealDetailScreen(navigateUp = {
                findNavController().popBackStack()
            }, mealType = mealTypeKey, record = record, viewModel = viewModel)
        }
    }
}