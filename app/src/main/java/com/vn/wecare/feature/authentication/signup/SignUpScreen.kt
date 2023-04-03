package com.vn.wecare.feature.authentication.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    moveToOnboardingScreen: () -> Unit,
    navigateBack: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val signUpUiState = viewModel.signUpUiState.collectAsState()
    val focusManager = LocalFocusManager.current

    signUpUiState.value.signUpResponse.let {
        when (it) {
            is Response.Loading -> LoadingDialog(loading = it == Response.Loading) {}
            is Response.Success -> viewModel.handleSignUpSuccess(moveToOnboardingScreen)
            is Response.Error -> viewModel.handleSignUpError()
            null -> { /* do nothing */
            }
        }
    }

    signUpUiState.value.snackbarMessageRes?.let {
        val snackBarMessage = stringResource(id = it)
        LaunchedEffect(scaffoldState, viewModel, it, snackBarMessage) {
            scaffoldState.snackbarHostState.showSnackbar(snackBarMessage)
            viewModel.snackbarMessageShown()
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(58.dp)
                    .padding(start = 24.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = "Create account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )

            CustomTextField(
                hint = stringResource(id = R.string.email_hint),
                label = "Email",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                leadingIcon = Icons.Default.Email,
                value = signUpUiState.value.email,
                onValueChange = viewModel::onEmailChange,
                isError = !signUpUiState.value.isEmailValid,
                errorMessage = stringResource(
                    id = viewModel.getEmailErrorMessage() ?: R.string.blank_text_id
                )
            )

            CustomTextField(
                hint = stringResource(id = R.string.username_hint),
                label = "User Name",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                leadingIcon = Icons.Default.Person,
                value = signUpUiState.value.userName,
                onValueChange = viewModel::onUserNameChange,
                isError = !signUpUiState.value.isUserNameValid,
                errorMessage = stringResource(
                    id = viewModel.getUsernameErrorMessage() ?: R.string.blank_text_id
                )
            )

            CustomTextField(
                hint = stringResource(id = R.string.password_hint),
                label = "Password",
                backgroundColor = Color.White,
                cursorColor = MaterialTheme.colors.primary,
                leadingIcon = Icons.Filled.VpnKey,
                trailingIcon = if (viewModel.isPasswordShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                value = signUpUiState.value.password,
                onValueChange = viewModel::onPasswordChange,
                isTextVisible = viewModel.isPasswordShow,
                onShowTextClick = viewModel::onShowPasswordClick,
                isError = !signUpUiState.value.isPasswordValid,
                errorMessage = stringResource(
                    id = viewModel.getPasswordErrorMessage() ?: R.string.blank_text_id
                )
            )

            CustomButton(
                text = "SIGN UP",
//                onClick = viewModel::onSignUpClick,
                onClick = moveToOnboardingScreen,
                textColor = Color.White,
            )

            Row(
                modifier = Modifier.padding(start = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                TextButton(onClick = navigateBack) {
                    Text(
                        text = " Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}