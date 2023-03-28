package com.vn.wecare.feature.authentication.forgotpassword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ForgotPasswordViewModel,
    moveToSendSuccessEmailScreen: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val uiState = viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    uiState.value.sendRecoveryEmailResponse.let {
        when (it) {
            is Response.Loading -> LoadingDialog(loading = it == Response.Loading) {}
            is Response.Success -> viewModel.handleSendRecoverySuccess(moveToSendSuccessEmailScreen)
            is Response.Error -> viewModel.handleSendRecoveryError()
            else -> { /* do nothing */
            }
        }
    }

    uiState.value.snackbarMessageRes?.let {
        val snackbarMessage = stringResource(id = it)
        LaunchedEffect(scaffoldState, viewModel, it, snackbarMessage) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)
            viewModel.snackbarMessageShown()
        }
    }

    Scaffold(
        modifier = modifier, backgroundColor = MaterialTheme.colors.background, topBar = {
            ForgotPasswordAppBar(modifier = modifier) { navigateUp() }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.padding(vertical = normalPadding, horizontal = mediumPadding),
                painter = painterResource(id = R.drawable.forgot_password),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.forgot_password_message),
                style = MaterialTheme.typography.body1,
                modifier = modifier.padding(horizontal = mediumPadding)
            )
            CustomTextField(
                hint = "Enter your email",
                label = "Email",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Gray,
                leadingIcon = Icons.Default.Email,
                padding = mediumPadding,
                value = uiState.value.email,
                onValueChange = viewModel::onEmailChange,
                isError = !uiState.value.isEmailValid,
                errorMessage = stringResource(id = R.string.email_error_message)
            )
            CustomButton(
                text = "Send recovery email",
                onClick = { viewModel.onSendRecoveryEmailClick() },
                textColor = Color.White,
                padding = mediumPadding
            )
        }
    }
}

@Composable
fun ForgotPasswordAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = tinyPadding)
            .background(color = MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null
            )
        }
        Text(
            modifier = modifier.padding(horizontal = smallPadding),
            text = "Forgot password",
            style = MaterialTheme.typography.h4,
        )
    }
}