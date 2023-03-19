package com.vn.wecare.feature.account.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAccountBinding
import com.vn.wecare.feature.account.AccountViewModel

class AccountFragment :
    BaseBindingFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.accountComposeView
        ) {
            AccountScreen(
                moveToSignInScreen = {
                    findNavController().navigate(R.id.action_accountFragment2_to_authentication_nested_graph)
                }, viewModel = accountViewModel
            )
        }
    }

    companion object {
        const val AccountFlowTAG = "Account flow"
    }
}