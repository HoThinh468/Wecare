package com.vn.wecare.feature.account.view.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAccountBinding
import com.vn.wecare.feature.account.viewmodel.AccountViewModel
import com.vn.wecare.utils.safeNavigate

class AccountFragment :
    BaseBindingFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        accountViewModel.updateAccountScreen()
        super.setupComposeView(
            binding.accountComposeView
        ) {
            AccountScreen(
                moveToSignInScreen = {
                    findNavController().apply {
                        popBackStack()
                        safeNavigate(
                            R.id.homeFragment, R.id.action_homeFragment_to_splashFragment
                        )
                    }
                },
                onEditInfoClick = {
                    findNavController().safeNavigate(
                        R.id.accountFragment, R.id.action_accountFragment_to_editInformationFragment
                    )
                },
                onAboutUsClick = {
                    findNavController().safeNavigate(
                        R.id.accountFragment, R.id.action_accountFragment_to_aboutUsFragment
                    )
                },
                onViewGoalClick = {
                    findNavController().safeNavigate(
                        R.id.accountFragment, R.id.action_accountFragment_to_goalHistoryFragment
                    )
                },
                viewModel = accountViewModel,
            )
        }
    }

    companion object {
        const val AccountFlowTAG = "Account flow"
    }
}