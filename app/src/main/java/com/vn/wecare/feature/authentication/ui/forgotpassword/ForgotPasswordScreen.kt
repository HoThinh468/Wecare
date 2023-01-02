package com.vn.wecare.feature.authentication.ui.forgotpassword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: ForgotPasswordViewModel,
    moveToSendSuccessEmailScreen: () -> Unit
) {

    val uiState by viewModel.uiState

    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            ForgotPasswordAppBar(modifier = modifier) { navigateUp() }
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
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
                value = uiState.email,
                onValueChange = viewModel::onEmailChange
            )
            CustomButton(
                text = "Send recovery email",
                onClick = { viewModel.onSendRecoveryEmailClick(moveToSendSuccessEmailScreen) },
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