package com.vn.wecare.feature.account.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentEditInformationBinding
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel

class EditInformationFragment : BaseBindingFragment<FragmentEditInformationBinding>(
    FragmentEditInformationBinding::inflate
) {

    private val viewModel: EditInfoViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initEditInfoScreenUiState()
        super.setupComposeView(
            binding.editInfoComposeView
        ) {
            EditInfoScreen(
                navigateBack = { findNavController().popBackStack() }, viewModel = viewModel
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetUiState()
    }

    companion object {
        const val editInfoTag = "Edit info tag"
    }
}