package com.vn.wecare.feature.authentication.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField
import com.vn.wecare.utils.common_composable.LoadingDialog

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel, moveToHomeScreen: () -> Unit, navigateBack: () -> Unit
) {

    val signUpUiState by viewModel.signUpUiState

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (signUpUiState.isLoading) {
            LoadingDialog(loading = signUpUiState.isLoading) {}
        }

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
            focusedIndicatorColor = Color.Gray,
            leadingIcon = Icons.Default.Email,
            value = signUpUiState.email,
            onValueChange = viewModel::onEmailChange
        )

        CustomTextField(
            hint = stringResource(id = R.string.username_hint),
            label = "User Name",
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Gray,
            leadingIcon = Icons.Default.Person,
            value = signUpUiState.userName,
            onValueChange = viewModel::onUserNameChange
        )

        CustomTextField(
            hint = stringResource(id = R.string.password_hint),
            label = "Password",
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Gray,
            leadingIcon = Icons.Filled.VpnKey,
            trailingIcon = if (signUpUiState.isPasswordShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            value = signUpUiState.password,
            onValueChange = viewModel::onPasswordChange,
            isTextVisible = signUpUiState.isPasswordShow,
            onShowTextClick = viewModel::onShowPasswordClick
        )

        CustomButton(
            text = "SIGN UP",
            onClick = { viewModel.onSignUpClick { moveToHomeScreen() } },
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