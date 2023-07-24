package com.vn.wecare.feature.account.view.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.viewmodel.AccountUiState
import com.vn.wecare.feature.account.viewmodel.AccountViewModel
import com.vn.wecare.ui.theme.cutCornerShape
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.common_composable.CardListTile
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    moveToSignInScreen: () -> Unit,
    viewModel: AccountViewModel,
    onEditInfoClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onViewGoalClick: () -> Unit
) {

    val uiState = viewModel.accountUiState.collectAsState()

    uiState.value.signOutResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> viewModel.handleSignOutSuccess {
                moveToSignInScreen()
            }

            is Response.Error -> viewModel.handleSignOutError()
            null -> {/* do nothing */
            }
        }
    }

    uiState.value.isReAuthenticateDialogShow.let {
        if (it) {
            ReAuthenticateDialog(
                modifier = modifier,
                onDismissDialog = viewModel::onDismissReAuthenticateDialog,
                viewModel = viewModel
            )
        }
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            AccountHeader(modifier = modifier,
                uiState = uiState,
                sendVerifiedEmail = viewModel::sendVerificationEmail,
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(halfMidPadding),
        ) {
            AccountBody(
                modifier = modifier,
                onSignOutClick = { viewModel.onSignOutClick() },
                onChangePasswordClick = viewModel::onChangePasswordClick,
                onAboutUsClick = onAboutUsClick,
                onEditInfoClick = onEditInfoClick,
                onViewGoalClick = onViewGoalClick
            )
        }
    }
}

@Composable
fun AccountHeader(
    modifier: Modifier,
    uiState: State<AccountUiState>,
    sendVerifiedEmail: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = tinyPadding)
                .background(color = MaterialTheme.colors.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier.padding(horizontal = smallPadding),
                text = stringResource(id = R.string.account_title),
                style = MaterialTheme.typography.h4,
            )
        }
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
                .background(MaterialTheme.colors.primary, shape = cutCornerShape)
                .height(100.dp)
                .width(120.dp)
                .padding(vertical = halfMidPadding)
        ) {
            Text(
                modifier = modifier.padding(top = smallPadding),
                text = uiState.value.userNameLogo,
                style = MaterialTheme.typography.h1.copy(fontSize = 40.sp),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Text(
            text = uiState.value.username,
            style = MaterialTheme.typography.h3,
            modifier = modifier.padding(top = normalPadding)
        )
        Text(
            text = uiState.value.email,
            style = MaterialTheme.typography.body1,
            color = colorResource(
                id = R.color.Black450
            ),
            modifier = modifier.padding(bottom = normalPadding)
        )
    }
}

@Composable
fun AccountBody(
    modifier: Modifier,
    onSignOutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onEditInfoClick: () -> Unit,
    onViewGoalClick: () -> Unit,
    onAboutUsClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_baseline_key,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.change_password,
            subTitle = "Click here to change your password",
            elevation = 0.dp,
            colorIconRes = R.color.Orange300,
            onClick = onChangePasswordClick
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_edit,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.edit_information,
            subTitle = "Change your height and weight",
            elevation = 0.dp,
            colorIconRes = R.color.Pink,
            onClick = onEditInfoClick
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_stars,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.goal_history,
            subTitle = "View all your goals and their status",
            elevation = 0.dp,
            colorIconRes = R.color.Yellow100,
            onClick = onViewGoalClick
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_question_mark,
            trailingIconRes = R.drawable.ic_arrow_forward,
            titleRes = R.string.about_us,
            subTitle = "Check more about us",
            elevation = 0.dp,
            colorIconRes = R.color.Blue400,
            onClick = onAboutUsClick
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        CardListTile(
            modifier = modifier,
            leadingIconRes = R.drawable.ic_logout,
            trailingIconRes = null,
            titleRes = R.string.logout,
            subTitle = null,
            elevation = 0.dp,
            colorIconRes = R.color.Red400,
            onClick = onSignOutClick
        )
    }
}